package com.anonymizer.datasources.controller;

import com.anonymizer.datasources.model.TableInfo;
import com.anonymizer.datasources.service.JdbcQueriesService;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(JdbcQueriesController.class)
public class JdbcQueriesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JdbcQueriesService mockJdbcQueriesService;

    @Test
    public void should_ReturnConnectionStatus_When_RequestIsValid() throws Exception {
        when(mockJdbcQueriesService.getConnexionStatus("test-datasource"))
                .thenReturn(true);
        mockMvc.perform(get("/api/v1/datasource/jdbc/status/{name}", "test-datasource")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    public void should_ListTablesInfo_When_RequestIsValid() throws Exception {
        TableInfo testTableInfo = new TableInfo("test-name", "test-type", "test-size");
        List<TableInfo> resultTablesInfo = Arrays.asList(testTableInfo);

        given(mockJdbcQueriesService.getTables("test-datasource"))
                .willReturn(resultTablesInfo);
        mockMvc.perform(get("/api/v1/datasource/jdbc/tables/{name}", "test-datasource")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].tableName", is(testTableInfo.getTableName())));
    }

}
