package com.anonymizer.datasources.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableInfo {
    private  String tableName;
    private String tableType;
    private String tableSize;
}
