package com.pblgllgs.testingsb3.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pblgllgs.testingsb3.model.Employee;
import com.pblgllgs.testingsb3.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper mapper;


    @Test
    @DisplayName("JUnit test for create employee operation in controller layer")
    void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        //given -  precondition or setup
        Employee employee = Employee.builder()
                .firstName("employee")
                .lastName("employee")
                .email("employee@gmail.com")
                .build();
        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
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
    @DisplayName("JUnit test for get all employees operation in controller layer")
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
        given(employeeService.getAllEmployees()).willReturn(employees);
        // when - action or the behaviour that we are going the
        ResultActions response = mockMvc.perform(get("/api/employees"));
        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(employees.size())));
    }

    @Test
    @DisplayName("JUnit test for get employee by id operation in controller layer")
    void givenEmployeeId_whenFindEmployeeById_thenReturnEmployeeObject() throws Exception {
        //given -  precondition or setup
        Long employeeId =1L;
        Employee employee = Employee.builder()
                .firstName("employee")
                .lastName("employee")
                .email("employee@gmail.com")
                .build();
        given(employeeService.findById(employeeId)).willReturn(Optional.of(employee));
        // when - action or the behaviour that we are going the
        ResultActions response = mockMvc.perform(get("/api/employees/{employeeId}",employeeId));
        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())));
    }

    @Test
    @DisplayName("JUnit test for get employee by id operation and throw exception in controller layer")
    void givenEmployeeId_whenFindEmployeeById_thenReturn404Status() throws Exception {
        //given -  precondition or setup
        Long employeeId =1L;
        given(employeeService.findById(employeeId)).willReturn(Optional.empty());
        // when - action or the behaviour that we are going the
        ResultActions response = mockMvc.perform(get("/api/employees/{employeeId}",employeeId));
        // then
        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("JUnit test for get employee by id operation in controller layer")
    void givenEmployeeObject_whenUpdateEmployee_thenReturnEmployeeObject() throws Exception {
        //given -  precondition or setup
        Long employeeId =1L;
        Employee savedEmployee = Employee.builder()
                .firstName("employee")
                .lastName("employee")
                .email("employee@gmail.com")
                .build();
        Employee updatedEmployee = Employee.builder()
                .firstName("employee2")
                .lastName("employee2")
                .email("employee2@gmail.com")
                .build();
        given(employeeService.findById(employeeId)).willReturn(Optional.of(savedEmployee));
        given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
        // when - action or the behaviour that we are going the
        ResultActions response = mockMvc.perform(put("/api/employees/{employeeId}",employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedEmployee)));
        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())));
    }

    @Test
    @DisplayName("JUnit test for get employee by id operation in controller layer")
    void givenEmployeeObject_whenUpdateEmployee_thenReturn404Status() throws Exception {
        //given -  precondition or setup
        Long employeeId =1L;
        Employee updatedEmployee = Employee.builder()
                .firstName("employee2")
                .lastName("employee2")
                .email("employee2@gmail.com")
                .build();
        given(employeeService.findById(employeeId)).willReturn(Optional.empty());
        given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
        // when - action or the behaviour that we are going the
        ResultActions response = mockMvc.perform(put("/api/employees/{employeeId}",employeeId)
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
        Long employeeId =1L;
        willDoNothing().given(employeeService).deleteEmployee(employeeId);
        // when - action or the behaviour that we are going the
        ResultActions response = mockMvc.perform(delete("/api/employees/{employeeId}",employeeId));
        // then
        response.andDo(print())
                .andExpect(status().isOk());
    }

}