package com.anonymizer.datasources.controller;

import com.anonymizer.datasources.model.JdbcDataSource;
import com.anonymizer.datasources.service.JdbcDataSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/datasource")
@CrossOrigin(origins = "http://localhost:4200")
public class JdbcDataSourceController {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcDataSourceController.class);

    @Autowired
    JdbcDataSourceService dataSourceService;

    @PostMapping(path = "/datasource")
    public JdbcDataSource addDataSource(@Valid @NotNull @RequestBody JdbcDataSource dataSource) {
        LOG.info(String.format("Data source name: {}", dataSource.getName()));
        return  dataSourceService.createDataSource(dataSource);
    }

    @GetMapping(path = "/datasources")
    public Collection<JdbcDataSource> getAllDataSources() {
        return dataSourceService.getAllDataSources();
    }

    @GetMapping(path = "/{name}")
    public Optional<JdbcDataSource> getDataSourceByName(@PathVariable("name") String name) {
        return dataSourceService.getDataSourceByName(name);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteDataSourceById(@PathVariable("id") int id) {
        dataSourceService.deleteDataSourceById(id);
    }

    @PutMapping(path = "/{id}")
    public JdbcDataSource updateJdbcDataSource(@Valid @NotNull @RequestBody JdbcDataSource jdbcDataSource, @PathVariable("id") int id) {
        return  dataSourceService.updateDataSource(jdbcDataSource, id);
    }

    @DeleteMapping(path = "/datasource")
    public void deleteDataSources(@RequestBody Collection<JdbcDataSource> dataSources) {
        dataSourceService.deleteDataSources(dataSources);
    }
}
