package com.anonymizer.datasources.controller;

import com.anonymizer.datasources.model.JdbcDataSource;
import com.anonymizer.datasources.service.JdbcDataSourceService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
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

@RunWith(SpringRunner.class)
@WebMvcTest(JdbcDataSourceController.class)
public class TestJdbcDataSourceController {

    private static Logger logger = LoggerFactory.getLogger(TestJdbcDataSourceController.class);

    //private static JdbcDataSource testJdbcDataSource;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JdbcDataSourceService mockJdbcDataSourceService;

  /*  @BeforeAll
    public static void setUp() {
        testJdbcDataSource = new JdbcDataSource();
        testJdbcDataSource.setId(1);
        testJdbcDataSource.setUsername("test-username");
    }*/

    @Test
    public void should_CreateDatasource_When_RequestIsValid() throws Exception {

        JdbcDataSource testJdbcDataSource = new JdbcDataSource();
        testJdbcDataSource.setId(1);
        testJdbcDataSource.setUsername("test-username");

        when(mockJdbcDataSourceService.createDataSource(any(JdbcDataSource.class))).thenReturn(testJdbcDataSource);

        String content = "{\"id\": \"" + testJdbcDataSource.getId() + "\",\"username\": \"" + testJdbcDataSource.getUsername() + "\"}";
        logger.debug(content);

        mockMvc.perform(post("/api/v1/datasource/datasource")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.username").value("test-username"));
    }

    @Test
    public void should_ListDataSources_When_RequestIsValid() throws Exception {

        JdbcDataSource testJdbcDataSource = new JdbcDataSource();
        testJdbcDataSource.setId(1);
        testJdbcDataSource.setUsername("test-username");

        List<JdbcDataSource> allJdbcDataSources = Arrays.asList(testJdbcDataSource);

        given(mockJdbcDataSourceService.getAllDataSources()).willReturn(allJdbcDataSources);

        mockMvc.perform(get("/api/v1/datasource/datasources")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(testJdbcDataSource.getUsername())));
    }

    @Test
    public void should_GetDatasource_WhenRequestIsValid() throws Exception {

        JdbcDataSource testJdbcDataSource = new JdbcDataSource();
        testJdbcDataSource.setId(1);
        testJdbcDataSource.setUsername("test-username");


        String content = "{\"id\": \"" + testJdbcDataSource.getId() + "\",\"username\": \""
                + testJdbcDataSource.getUsername() + "\"}";

        logger.debug(content);

        when(mockJdbcDataSourceService.getDataSourceByName(testJdbcDataSource.getUsername()))
                .thenReturn(Optional.of(testJdbcDataSource));

        mockMvc.perform(get("/api/v1/datasource/{name}", "test-username")
                .content(content)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDelete_theDatasourceShouldBeDeleted() throws Exception {
        JdbcDataSource testJdbcDataSource = new JdbcDataSource();
        testJdbcDataSource.setId(1);
        testJdbcDataSource.setUsername("test-username");

        when(mockJdbcDataSourceService.getDataSourceByName(testJdbcDataSource.getName()))
                .thenReturn(Optional.of(testJdbcDataSource));
        mockJdbcDataSourceService.deleteDataSourceById(testJdbcDataSource.getId());
        // called only one time
        verify(mockJdbcDataSourceService, times(1)).deleteDataSourceById(testJdbcDataSource.getId());

        mockMvc.perform(delete("/api/v1/datasource/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_UpdateDatasource_When_IsValid() throws Exception {

        JdbcDataSource testJdbcDataSource = new JdbcDataSource();
        testJdbcDataSource.setId(1);
        testJdbcDataSource.setUsername("test-username");

        String content = "{\"id\": \"" + testJdbcDataSource.getId() + "\",\"username\": \""
                + testJdbcDataSource.getUsername() + "\"}";

        when(mockJdbcDataSourceService.updateDataSource(testJdbcDataSource, 1))
                .thenReturn(testJdbcDataSource);

        mockMvc.perform(put("/api/v1/datasource/{id}", "1")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.username").value("test-username"))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

}
