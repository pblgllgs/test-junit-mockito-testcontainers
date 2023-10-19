package com.pblgllgs.testingsb3.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pblgllgs.testingsb3.model.Employee;
import com.pblgllgs.testingsb3.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EmployeeControllerIT extends AbstractionContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        employeeRepository.deleteAll();
    }

    @Test
    @DisplayName("JUnit test for create employee operation")
    void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        //given -  precondition or setup
        Employee employee = Employee.builder()
                .firstName("employee")
                .lastName("employee")
                .email("employee@gmail.com")
                .build();

        // when - action or the behaviour that we are going the
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee)));
        // then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @Test
    @DisplayName("JUnit test for get all employees operation")
    void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeeList() throws Exception {
        //given -  precondition or setup
        Employee employee1 = Employee.builder()
                .firstName("employee1")
                .lastName("employee1")
                .email("employee1@gmail.com")
                .build();
        Employee employee2 = Employee.builder()
                .firstName("employee2")
                .lastName("employee2")
                .email("employee2@gmail.com")
                .build();
        List<Employee> employees = List.of(employee1, employee2);
        employeeRepository.saveAll(employees);
        // when - action or the behaviour that we are going the
        ResultActions response = mockMvc.perform(get("/api/employees"));
        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(employees.size())));
    }

    @Test
    @DisplayName("JUnit test for get all employees operation")
    void givenListOfEmployees_whenGetAllEmployees_thenReturnEmptyList() throws Exception {
        //given -  precondition or setup
        // when - action or the behaviour that we are going the
        ResultActions response = mockMvc.perform(get("/api/employees"));
        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(0)));
    }

    @Test
    @DisplayName("JUnit test for get employee by id operation")
    void givenEmployeeId_whenFindEmployeeById_thenReturnEmployeeObject() throws Exception {
        //given -  precondition or setup
        Employee employee = Employee.builder()
                .firstName("employee")
                .lastName("employee")
                .email("employee@gmail.com")
                .build();
        employeeRepository.save(employee);
        // when - action or the behaviour that we are going the
        ResultActions response = mockMvc.perform(get("/api/employees/{employeeId}", employee.getId()));
        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())));
    }

    @Test
    @DisplayName("JUnit test for get employee by id operation")
    void givenEmployeeId_whenFindEmployeeById_thenNotFoundEmployee() throws Exception {
        //given -  precondition or setup
        Long employeeId = 1L;
        // when - action or the behaviour that we are going the
        ResultActions response = mockMvc.perform(get("/api/employees/{employeeId}", employeeId));
        // then
        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("JUnit test for get employee by id operation")
    void givenEmployeeObject_whenUpdateEmployee_thenReturnEmployeeObject() throws Exception {
        //given -  precondition or setup
        Employee savedEmployee = Employee.builder()
                .firstName("employee")
                .lastName("employee")
                .email("employee@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);
        Employee updatedEmployee = Employee.builder()
                .firstName("employee2")
                .lastName("employee2")
                .email("employee2@gmail.com")
                .build();
        // when - action or the behaviour that we are going the
        ResultActions response = mockMvc.perform(put("/api/employees/{employeeId}", savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedEmployee)));
        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())));
    }

    @Test
    @DisplayName("JUnit test for update employee operation")
    void givenEmployeeObject_whenUpdateEmployee_thenReturnNotFound() throws Exception {
        //given -  precondition or setup
        long employeeId = 1L;
        Employee updatedEmployee = Employee.builder()
                .firstName("employee2")
                .lastName("employee2")
                .email("employee2@gmail.com")
                .build();
        // when - action or the behaviour that we are going the
        ResultActions response = mockMvc.perform(put("/api/employees/{employeeId}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedEmployee)));
        // then
        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("JUnit test for get employee by id operation in controller layer")
    void givenEmployeeObject_whenDeleteEmployee_thenReturn200Status() throws Exception {
        //given -  precondition or setup
        Employee savedEmployee = Employee.builder()
                .firstName("employee")
                .lastName("employee")
                .email("employee@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);
        // when - action or the behaviour that we are going the
        ResultActions response = mockMvc.perform(delete("/api/employees/{employeeId}", savedEmployee.getId()));
        // then
        response.andDo(print())
                .andExpect(status().isOk());
    }
}
