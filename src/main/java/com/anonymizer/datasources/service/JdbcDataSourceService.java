package com.anonymizer.datasources.service;

import com.anonymizer.datasources.model.JdbcDataSource;

import java.util.Collection;
import java.util.Optional;

public interface JdbcDataSourceService {

    // TODO (too many method break Interface Segregation principe)
    JdbcDataSource createDataSource(JdbcDataSource jdbcDataSource);
    Collection<JdbcDataSource> getAllDataSources();
    Optional<JdbcDataSource> getDataSourceByName(String name);
    JdbcDataSource updateDataSource(JdbcDataSource jdbcDataSource , int id);
    void deleteDataSource(String name);
    void deleteDataSourceById(int id);
    void deleteDataSources(Collection<JdbcDataSource> jdbcDataSources);
}
