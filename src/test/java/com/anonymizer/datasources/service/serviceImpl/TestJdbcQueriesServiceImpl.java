package com.anonymizer.datasources.service.serviceImpl;

import com.anonymizer.datasources.model.JdbcDataSource;
import com.anonymizer.datasources.service.JdbcQueriesService;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

public class TestJdbcQueriesServiceImpl {

    private JdbcDataSource testJdbcDataSource;

    @MockBean
    private JdbcQueriesService mockJdbcQueriesService;

    @Test
    public void testSetDriver() {

    }
}
