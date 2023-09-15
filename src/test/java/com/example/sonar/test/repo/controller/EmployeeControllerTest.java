package com.example.sonar.test.repo.controller;

import com.example.sonar.test.repo.model.Employee;
import com.example.sonar.test.repo.repository.EmployeeRepository;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("integration-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerTest {

    MockMvc mockMvc;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeController employeeController;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        RestAssuredMockMvc.basePath = "http://localhost:" + port;
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        RestAssuredMockMvc.mockMvc(mockMvc);
        employeeRepository.deleteAll();
    }

    @Test
    void getAllEmployees() throws Exception {
        List<Employee> employees = List.of(
                new Employee(null, "Dean", "Winchester", "dean.winchester@mail.com"),
                new Employee(null, "Sam", "Winchester", "sam.winchester@mail.com")
        );
        employeeRepository.saveAll(employees);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emp/employees"))
                .andExpect(status().isOk())
                .andReturn();
    }
}