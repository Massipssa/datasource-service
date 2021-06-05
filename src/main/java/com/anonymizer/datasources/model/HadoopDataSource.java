package com.anonymizer.datasources.model;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "hadoop_sources")
@Data
public class HadoopDataSource extends GenericDataSource {
    private String owner;
    private String databaseName;
    private String path;
}
