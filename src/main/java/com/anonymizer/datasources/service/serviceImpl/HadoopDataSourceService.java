package com.anonymizer.datasources.service.serviceImpl;

import com.anonymizer.datasources.exception.DataSourceExistsException;
import com.anonymizer.datasources.model.HadoopDataSource;
import com.anonymizer.datasources.repository.HadoopDatasourceRepository;
import com.anonymizer.datasources.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
public class HadoopDataSourceService implements DataSourceService<HadoopDataSource> {

    @Autowired
    HadoopDatasourceRepository hadoopDatasourceRepository;

    @Override
    public HadoopDataSource createDataSource(HadoopDataSource hadoopDataSource) throws DataSourceExistsException {
        if(!(hadoopDatasourceRepository.findByName(hadoopDataSource.getName()).isPresent()))
        {
            hadoopDataSource.setCreationTime(LocalDateTime.now());
            hadoopDataSource.setUpdateTime(LocalDateTime.now());
            return hadoopDatasourceRepository.save(hadoopDataSource);
        }
        throw new DataSourceExistsException(hadoopDataSource);
    }

    @Override
    public Collection<HadoopDataSource> getAllDataSources() {
        return hadoopDatasourceRepository.findAll();
    }

    @Override
    public Optional<HadoopDataSource> getDataSourceByName(String name) {
        return hadoopDatasourceRepository.findByName(name);
    }

    @Override
    public HadoopDataSource updateDataSource(HadoopDataSource hadoopDataSource, int id) {
        //return null;
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteDataSourceById(int id) {
        hadoopDatasourceRepository.deleteById(id);
    }

    @Override
    public void deleteDataSources(Collection<HadoopDataSource> hadoopDataSources) {
        hadoopDatasourceRepository.deleteAll(hadoopDataSources);
    }
}
