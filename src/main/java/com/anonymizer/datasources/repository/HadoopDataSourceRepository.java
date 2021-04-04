package com.anonymizer.datasources.repository;

import com.anonymizer.datasources.model.HadoopDataSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HadoopDataSourceRepository extends JpaRepository<HadoopDataSource, Integer> {
    Optional<HadoopDataSource> findByName(final String name);
}
