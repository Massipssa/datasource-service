package com.anonymizer.datasources.controller;

import com.anonymizer.datasources.exception.DataSourceExistsException;
import com.anonymizer.datasources.model.GenericDataSource;
import com.anonymizer.datasources.model.HadoopDataSource;
import com.anonymizer.datasources.service.DataSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/datasource/hadoop")
@CrossOrigin(origins = "http://localhost:4200")
public class HadoopSourceController {

    private static final Logger logger = LoggerFactory.getLogger(HadoopSourceController.class);

    @Autowired
    DataSourceService<HadoopDataSource> dataSourceService;

    @PostMapping
    public HadoopDataSource addDataSource(@Valid @NotNull @RequestBody HadoopDataSource hadoopDataSource)
            throws DataSourceExistsException {
        logger.info(String.format("Data source name: {}", hadoopDataSource.getName()));
        return dataSourceService.createDataSource(hadoopDataSource);
    }

    @GetMapping(path = "/datasources")
    public Collection<HadoopDataSource> getAllDataSources() {
        return dataSourceService.getAllDataSources();
    }

    @GetMapping(path = "/{name}")
    public Optional<HadoopDataSource> getDataSourceByName(@PathVariable("name") String name) {
        return dataSourceService.getDataSourceByName(name);
    }

    @PutMapping(path = "/{id}")
    public HadoopDataSource updateJdbcDataSource(@Valid @NotNull @RequestBody HadoopDataSource hadoopDataSource, @PathVariable("id") int id) {
        return dataSourceService.updateDataSource(hadoopDataSource, id);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteDataSourceById(@PathVariable("id") int id) {
        dataSourceService.deleteDataSourceById(id);
    }

    @DeleteMapping(path = "/datasources")
    public void deleteDataSources(@RequestBody Collection<HadoopDataSource> hadoopDataSources) {
        dataSourceService.deleteDataSources(hadoopDataSources);
    }

}
