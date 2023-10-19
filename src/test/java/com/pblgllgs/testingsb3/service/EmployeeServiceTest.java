package com.pblgllgs.testingsb3.service;


import com.pblgllgs.testingsb3.exception.EmployeeAlreadyExistsException;
import com.pblgllgs.testingsb3.model.Employee;
import com.pblgllgs.testingsb3.repository.EmployeeRepository;
import com.pblgllgs.testingsb3.service.impl.EmployeeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    private Employee employee;

    @BeforeEach
    void setup() {
        employee = Employee.builder()
                .id(1L)
                .firstName("employee")
                .lastName("employee")
                .email("employee@gmail.com")
                .build();
    }

    @Test
    @DisplayName("JUnit test for save employee operation in service layer")
    void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee() {
        //given
        log.info(employee.toString());
        given(
                employeeRepository.findEmployeeByEmail(
                        employee.getEmail()
                )
        ).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);
        //when
        Employee savedEmployee = employeeService.saveEmployee(employee);

        //when
        log.info(savedEmployee.toString());
        assertThat(savedEmployee).isNotNull().isEqualTo(employee);
    }

    @Test
    @DisplayName("JUnit test for save employee operation in service layer")
    void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
        //given
        given(
                employeeRepository.findEmployeeByEmail(
                        employee.getEmail()
                )
        ).willReturn(Optional.of(employee));

        //when
        assertThrows(EmployeeAlreadyExistsException.class, () -> employeeService.saveEmployee(employee));

        //when

        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("JUnit test for find all employees operation in service layer")
    void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeesList() {
        //given -  precondition or setup
        Employee employee2 = Employee.builder()
                .id(2L)
                .firstName("employee2")
                .lastName("employee2")
                .email("employee2@gmail.com")
                .build();
        List<Employee> actualEmployees = List.of(employee, employee2);
        given(employeeRepository.findAll()).willReturn(actualEmployees);
        // when - action or the behaviour that we are going the
        List<Employee> employeesList = employeeService.getAllEmployees();
        // then
        assertThat(employeesList).isNotEmpty().hasSize(actualEmployees.size());
    }

    @Test
    @DisplayName("JUnit test for find all employees operation, but is empty, in service layer")
    void givenEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
        //given -  precondition or setup

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());
        // when - action or the behaviour that we are going the
        List<Employee> employeesList = employeeService.getAllEmployees();
        // then
        assertThat(employeesList).isEmpty();
    }

    @Test
    @DisplayName("JUnit test for find employee by id operation in service layer")
    void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
        //given -  precondition or setup

        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));
        // when - action or the behaviour that we are going the
        Optional<Employee> employeeDB = employeeService.findById(employee.getId());
        // then
        assertThat(employeeDB).isNotNull().isEqualTo(Optional.of(employee));
    }

    @Test
    @DisplayName("JUnit test for update employee operation in service layer")
    void givenEmployeeObject_whenUpdateEmployee_thenReturnEmployeeObject(){
        //given -  precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("test@gmail.com");
        // when - action or the behaviour that we are going the
        Employee employeeDB = employeeService.updateEmployee(employee);
        // then
        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    @DisplayName("JUnit test for delete employee operation in service layer")
    void givenEmployeeObject_whenDeleteEmployee_thenReturnEmployeeObject(){
        //given -  precondition or setup
        long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(employeeId);
        // when - action or the behaviour that we are going the
        employeeService.deleteEmployee(employeeId);
        // then
        verify(employeeRepository,times(1)).deleteById(employeeId);
    }
}