package com.anonymizer.datasources.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity(name = "jdbc_sources")
@Data
public class JdbcDataSource extends GenericDataSource {

    private String jdbcUrl;
    private String username;
    private String password;
    private String driver;

   /* public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }*/
}
