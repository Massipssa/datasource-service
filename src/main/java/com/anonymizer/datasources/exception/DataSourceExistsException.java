package com.anonymizer.datasources.exception;

import com.anonymizer.datasources.model.GenericDataSource;

public class DataSourceExistsException extends Exception {

    public DataSourceExistsException(GenericDataSource genericDataSource) {
        super("The datasource " + genericDataSource.getName() + " already exists");
    }
}
