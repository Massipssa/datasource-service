package com.anonymizer.datasources.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DataType {

    @JsonProperty("HDFS")
    HDFS,
    @JsonProperty("Hive")
    HIVE,
    @JsonProperty("HBase")
    HBASE,
    @JsonProperty("Kafka")
    KAFKA,
    @JsonProperty("Oracle")
    ORACLE,
    @JsonProperty("PostgreSQL")
    POSTGRESQL,
    @JsonProperty("MySQL")
    MYSQL,
    @JsonProperty("SqlServer")
    SQL_SERVER
}
