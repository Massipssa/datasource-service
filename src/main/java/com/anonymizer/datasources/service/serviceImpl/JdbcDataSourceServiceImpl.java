package com.anonymizer.datasources.service.serviceImpl;

import com.anonymizer.datasources.exception.DataSourceExistsException;
import com.anonymizer.datasources.model.JdbcDataSource;
import com.anonymizer.datasources.repository.JdbcDataSourceRepository;
import com.anonymizer.datasources.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

/**
 * TODO:
    * Encrypt connection pwd
 */

@Service
public class JdbcDataSourceServiceImpl implements DataSourceService<JdbcDataSource> {

    @Autowired
    JdbcDataSourceRepository jdbcDataSourceRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public JdbcDataSource createDataSource(JdbcDataSource jdbcDataSource) throws DataSourceExistsException {
        if(!(jdbcDataSourceRepository.findByName(jdbcDataSource.getName()).isPresent()))
        {
            String pwd = jdbcDataSource.getPassword();
            //jdbcDataSource.setPassword(passwordEncoder.encode(pwd));
            jdbcDataSource.setCreationTime(LocalDateTime.now());
            jdbcDataSource.setUpdateTime(LocalDateTime.now());
            return jdbcDataSourceRepository.save(jdbcDataSource);
        }
        throw new DataSourceExistsException(jdbcDataSource);
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
    public void deleteDataSourceById(int id) {
        jdbcDataSourceRepository.deleteById(id);
    }

    @Override
    public void deleteDataSources(Collection<JdbcDataSource> jdbcDataSources) {
        jdbcDataSourceRepository.deleteAll(jdbcDataSources);
    }
}
