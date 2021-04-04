package com.anonymizer.datasources.service;

import com.anonymizer.datasources.exception.DataSourceExistsException;
import com.anonymizer.datasources.model.JdbcDataSource;

import java.util.Collection;
import java.util.Optional;

public interface DataSourceService {

    JdbcDataSource createDataSource(JdbcDataSource dataSource) throws DataSourceExistsException;
    Collection<JdbcDataSource> getAllDataSources();
    Optional<JdbcDataSource> getDataSourceByName(String name);
    JdbcDataSource updateDataSource(JdbcDataSource dataSource , int id);
    void deleteDataSourceById(int id);
    void deleteDataSources(Collection<JdbcDataSource> dataSources);
}
