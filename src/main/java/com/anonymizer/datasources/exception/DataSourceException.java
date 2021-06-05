package com.anonymizer.datasources.exception;

/**
 *
 */

public class DataSourceException extends RuntimeException {

    public DataSourceException() { super();}
    public DataSourceException(String message) { super(message); }
    public DataSourceException(String message, Throwable t) { super(message, t); }
    public DataSourceException(Throwable t) { super(t); }

}
