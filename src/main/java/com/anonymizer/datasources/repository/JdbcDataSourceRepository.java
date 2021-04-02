package com.anonymizer.datasources.repository;

import com.anonymizer.datasources.model.JdbcDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JdbcDataSourceRepository extends JpaRepository<JdbcDataSource, Integer> {
    Optional<JdbcDataSource> findByName(final String name);
}
