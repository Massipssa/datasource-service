package com.anonymizer.datasources.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity(name =  "jdbc_sources")
@Data
public class JdbcDataSource extends GenericDataSource {

    private String jdbcUrl;
    private String username;
    private String password;
    private String driver;
}
