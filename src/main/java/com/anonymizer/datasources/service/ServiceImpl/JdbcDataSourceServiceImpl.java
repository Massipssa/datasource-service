package com.anonymizer.datasources.service.ServiceImpl;

import com.anonymizer.datasources.model.JdbcDataSource;
import com.anonymizer.datasources.repository.JdbcDataSourceRepository;
import com.anonymizer.datasources.service.JdbcDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

/**
 * TODO:
    * Don't save connection with same name
    * Encrypt connection pwd
 */

@Service
public class JdbcDataSourceServiceImpl implements JdbcDataSourceService {


    @Autowired
    JdbcDataSourceRepository jdbcDataSourceRepository;

    @Override
    public JdbcDataSource createDataSource(JdbcDataSource jdbcDataSource) {
        jdbcDataSource.setCreationTime(LocalDateTime.now());
        jdbcDataSource.setUpdateTime(LocalDateTime.now());
        return jdbcDataSourceRepository.save(jdbcDataSource);
    }

    @Override
    public Collection<JdbcDataSource> getAllDataSources() {
        return jdbcDataSourceRepository.findAll();
    }

    @Override
    public Optional<JdbcDataSource> getDataSourceByName(String name) {
        return jdbcDataSourceRepository.findByName(name);
    }

    @Override
    public JdbcDataSource updateDataSource(JdbcDataSource jdbcDataSource, int id) {
        return jdbcDataSourceRepository.findById(id)
                .map(u -> {
                    u.setPassword(jdbcDataSource.getPassword());
                    u.setUsername(jdbcDataSource.getUsername());
                    u.setJdbcUrl(jdbcDataSource.getJdbcUrl());
                    return jdbcDataSourceRepository.save(u);
                })
                .orElse(null);
    }

    @Override
    public void deleteDataSource(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteDataSourceById(int id) {
        jdbcDataSourceRepository.deleteById(id);
    }

    @Override
    public void deleteDataSources(Collection<JdbcDataSource> jdbcDataSources) {
        throw new UnsupportedOperationException();
    }
}
