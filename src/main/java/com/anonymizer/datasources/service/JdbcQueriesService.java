package com.anonymizer.datasources.service;


import com.anonymizer.datasources.model.TableInfo;

import java.sql.SQLException;
import java.util.Collection;

public interface JdbcQueriesService {

    boolean getConnexionStatus(String dataSourceName) throws SQLException;
    Collection<TableInfo> getTables(String JdbcDataSourceName);

}
