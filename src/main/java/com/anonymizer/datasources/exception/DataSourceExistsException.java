package com.anonymizer.datasources.exception;

import com.anonymizer.datasources.model.GenericDataSource;

public class DataSourceExistsException extends DataSourceException {

    public DataSourceExistsException(GenericDataSource genericDataSource) {
        super("The datasource " + genericDataSource.getName() + " already exists");
    }
}
