package com.anonymizer.datasources.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;


@MappedSuperclass
@Data
public class GenericDataSource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private DataType type;
    private LocalDateTime creationTime;
    private LocalDateTime updateTime;
    private String description;
    
}
