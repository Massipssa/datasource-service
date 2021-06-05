package com.anonymizer.datasources.model;

public enum FileFormat {

    PARQUET(".parquet"),
    ORC(".orc"),
    JSON(".json"),
    CSV(".csv");

    private final String extension;

    FileFormat(String extension) {this.extension = extension;}

    public String getFileExtension () {
        return extension;
    }
 }
