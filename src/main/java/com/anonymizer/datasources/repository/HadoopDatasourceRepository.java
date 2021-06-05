package com.anonymizer.datasources.repository;

import com.anonymizer.datasources.model.HadoopDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HadoopDatasourceRepository extends JpaRepository<HadoopDataSource, Integer> {
    Optional<HadoopDataSource> findByName(final String name);
}
