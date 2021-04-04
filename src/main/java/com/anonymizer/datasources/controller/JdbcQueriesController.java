package com.anonymizer.datasources.controller;

import com.anonymizer.datasources.model.TableInfo;
import com.anonymizer.datasources.service.JdbcQueriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/datasource/jdbc")
@CrossOrigin(origins = "http://localhost:4200")
public class JdbcQueriesController {

    @Autowired
    JdbcQueriesService jdbcQueriesService;

    @GetMapping(path = "/status/{name}")
    public @ResponseBody boolean getConnexionStatus(@PathVariable("name") String dataSourceName) throws SQLException {
        return  jdbcQueriesService.getConnexionStatus(dataSourceName);
    }

    @GetMapping(path = "/tables/{name}")
    public @ResponseBody Collection<TableInfo> getTablesInfo(@PathVariable("name") String dataSourceName) {
        return jdbcQueriesService.getTables(dataSourceName);
    }
}
