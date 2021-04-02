package com.anonymizer.datasources.service.serviceImpl;

import com.anonymizer.datasources.model.JdbcDataSource;
import com.anonymizer.datasources.model.TableInfo;
import com.anonymizer.datasources.repository.JdbcDataSourceRepository;
import com.anonymizer.datasources.service.JdbcQueriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 * TODO: add logger
 */

@Service
public class JdbcQueriesServiceImpl implements JdbcQueriesService {

    private static Logger logger = LoggerFactory.getLogger(JdbcQueriesServiceImpl.class);

    private static String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private static String POSTGRES_DRIVER = "org.postgresql.Driver";
    private static String SQL_SERVER_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDrive";


    @Autowired
    JdbcDataSourceRepository jdbcDataSourceRepository;

    DataSource dataSource(JdbcDataSource jdbcDataSource) {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(jdbcDataSource.getJdbcUrl());
        driverManagerDataSource.setUsername(jdbcDataSource.getUsername());
        driverManagerDataSource.setPassword(jdbcDataSource.getPassword());
        driverManagerDataSource.setDriverClassName(jdbcDataSource.getDriver());
        return driverManagerDataSource;
    }

    public void setDriver(JdbcDataSource jdbcDataSource) {
        switch (jdbcDataSource.getType()) {
            case ORACLE:
                jdbcDataSource.setDriver(ORACLE_DRIVER);
                break;
            case MYSQL:
                jdbcDataSource.setDriver(MYSQL_DRIVER);
                break;
            case POSTGRESQL:
                jdbcDataSource.setDriver(POSTGRES_DRIVER);
                break;
            case SQL_SERVER:
                jdbcDataSource.setDriver(SQL_SERVER_DRIVER);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean getConnexionStatus(String dataSourceName) throws SQLException {

        Connection conn = null;
        Optional<JdbcDataSource> jdbcDataSource = jdbcDataSourceRepository.findByName(dataSourceName);

        if(jdbcDataSource.isPresent()) {
            try {
                JdbcDataSource tmpDataSource = jdbcDataSource.get();
                switch (tmpDataSource.getType()) {
                    case ORACLE:
                        Class.forName(ORACLE_DRIVER);
                        break;
                    case MYSQL:
                        Class.forName(MYSQL_DRIVER);
                        break;
                    case POSTGRESQL:
                        Class.forName(POSTGRES_DRIVER);
                        break;
                    case SQL_SERVER:
                        Class.forName(SQL_SERVER_DRIVER);
                        break;
                    default:
                        break;
                }
                conn = DriverManager.getConnection(
                        tmpDataSource.getJdbcUrl(),
                        tmpDataSource.getUsername(),
                        tmpDataSource.getPassword());
                return true;
            } catch (Exception e) {
                if (conn != null) {
                    conn.close();
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public Collection<TableInfo> getTables(String JdbcDataSourceName) {
        Optional<JdbcDataSource> jdbcDataSource = jdbcDataSourceRepository.findByName(JdbcDataSourceName);
        String sqlRequest = null;
        List<TableInfo> tablesInfo = new ArrayList<>();

        if(jdbcDataSource.isPresent()) {
            JdbcDataSource source = jdbcDataSource.get();
            setDriver(source);
            JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource(source));
            sqlRequest = "select table_name, table_type, pg_size_pretty(pg_relation_size(quote_ident(table_name))) " +
                    "from information_schema.tables " +
                    "where table_schema = 'public'";

            jdbcTemplate.execute(sqlRequest);
            // TODO use streams
            jdbcTemplate.query(sqlRequest, resultSet -> {
                String tableName = resultSet.getString("table_name");
                String tableType = resultSet.getString("table_type");
                String tableSize = resultSet.getString("pg_size_pretty");
                tablesInfo.add(new TableInfo(tableName, tableType, tableSize));
            });
        }
        return tablesInfo;
    }
}
