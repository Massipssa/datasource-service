package com.anonymizer.datasources.controller;

import com.anonymizer.datasources.model.JdbcDataSource;
import com.anonymizer.datasources.service.DataSourceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * TODO: add tests
 *      - return 404 when datasource not exists
 */

@RunWith(SpringRunner.class)
@WebMvcTest(JdbcDataSourceController.class)
public class JdbcDataSourceControllerTest {

    private static Logger logger = LoggerFactory.getLogger(JdbcDataSourceControllerTest.class);
    private static final ObjectMapper om = new ObjectMapper();

    private JdbcDataSource testJdbcDataSource;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DataSourceService mockDataSourceService;

    @Before
    public void setUp() {
        testJdbcDataSource = new JdbcDataSource();
        testJdbcDataSource.setId(1);
        testJdbcDataSource.setUsername("test-datasource");
    }

    @Test
    public void should_CreateDatasource_When_RequestIsValid() throws Exception {

        when(mockDataSourceService.createDataSource(any(JdbcDataSource.class))).thenReturn(testJdbcDataSource);

        String content = "{\"id\": \"" + testJdbcDataSource.getId() + "\",\"username\": \"" + testJdbcDataSource.getUsername() + "\"}";
        logger.debug(content);

        mockMvc.perform(post("/api/v1/datasource/jdbc/datasource")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.username").value("test-datasource"));
    }

    @Test
    public void should_ListDataSources_When_RequestIsValid() throws Exception {

        List<JdbcDataSource> allJdbcDataSources = Arrays.asList(testJdbcDataSource);
        given(mockDataSourceService.getAllDataSources()).willReturn(allJdbcDataSources);

        mockMvc.perform(get("/api/v1/datasource/jdbc/datasources")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(testJdbcDataSource.getUsername())));
    }

    @Test
    public void should_GetDatasource_WhenRequestIsValid() throws Exception {

        String content = "{\"id\": \"" + testJdbcDataSource.getId() + "\",\"username\": \""
                + testJdbcDataSource.getUsername() + "\"}";

        logger.debug(content);

        when(mockDataSourceService.getDataSourceByName(testJdbcDataSource.getUsername()))
                .thenReturn(Optional.of(testJdbcDataSource));

        mockMvc.perform(get("/api/v1/datasource/jdbc/{name}", "test-datasource")
                .content(content)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDelete_theDatasourceShouldBeDeleted() throws Exception {

        when(mockDataSourceService.getDataSourceByName(testJdbcDataSource.getName()))
                .thenReturn(Optional.of(testJdbcDataSource));
        mockDataSourceService.deleteDataSourceById(testJdbcDataSource.getId());
        // called only one time
        verify(mockDataSourceService, times(1))
                .deleteDataSourceById(testJdbcDataSource.getId());

        mockMvc.perform(delete("/api/v1/datasource/jdbc/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDelete_theDataSourcesShouldBeDeleted() throws Exception {

        List<JdbcDataSource> jdbcDataSourcesToDelete = Arrays.asList(testJdbcDataSource);
        String content = om.writeValueAsString(jdbcDataSourcesToDelete);
        logger.debug(content);

        mockDataSourceService.deleteDataSources(jdbcDataSourcesToDelete);
        verify(mockDataSourceService, times(1))
                .deleteDataSources(jdbcDataSourcesToDelete);

        mockMvc.perform(delete("/api/v1/datasource/jdbc/datasources")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void should_UpdateDatasource_When_IsValid() throws Exception {

        String content = "{\"id\": \"" + testJdbcDataSource.getId() + "\",\"username\": \""
                + testJdbcDataSource.getUsername() + "\"}";

        when(mockDataSourceService
                .updateDataSource(testJdbcDataSource, 1))
                .thenReturn(testJdbcDataSource);

        mockMvc.perform(put("/api/v1/datasource/jdbc/{id}", "1")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.username").value("test-datasource"))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

}
