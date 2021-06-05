package com.anonymizer.datasources.service;

import com.anonymizer.datasources.exception.DataSourceExistsException;
import com.anonymizer.datasources.model.GenericDataSource;

import java.util.Collection;
import java.util.Optional;

public interface DataSourceService<T> {

    T createDataSource(T dataSource) throws DataSourceExistsException;
    Collection<T> getAllDataSources();
    Optional<T> getDataSourceByName(String name);
    T updateDataSource(T dataSource , int id);
    void deleteDataSourceById(int id);
    void deleteDataSources(Collection<T> dataSources);
}
