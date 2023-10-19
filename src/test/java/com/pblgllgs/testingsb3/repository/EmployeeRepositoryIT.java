package com.pblgllgs.testingsb3.repository;

import com.pblgllgs.testingsb3.integration.AbstractionContainerBaseTest;
import com.pblgllgs.testingsb3.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryIT extends AbstractionContainerBaseTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void init() {
        employee = Employee.builder()
                .firstName("pbl")
                .lastName("gllgs")
                .email("pbl.gllgs@gmail.com")
                .build();
    }

    @Test
    @DisplayName("JUnit test for save employee operation")
    void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        Employee savedEmployee = employeeRepository.save(employee);
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isPositive();
        assertThat(savedEmployee.getFirstName()).isEqualTo("pbl");
        assertThat(savedEmployee.getLastName()).isEqualTo("gllgs");
        assertThat(savedEmployee.getEmail()).isEqualTo("pbl.gllgs@gmail.com");
    }

    @Test
    @DisplayName("JUnit test for find all employees operation")
    void givenEmployeesList_whenFindAll_thenReturnEmployeeList() {
        employeeRepository.save(employee);
        Employee admin = Employee.builder()
                .firstName("admin")
                .lastName("admin")
                .email("admin@gmail.com")
                .build();
        employeeRepository.save(admin);

        List<Employee> employees = employeeRepository.findAll();

        assertThat(employees).isNotEmpty().hasSize(2);
    }

    @Test
    @DisplayName("JUnit test for find employee by id operation")
    void givenEmployeeObject_whenFindEmployeeById_thenReturnEmployeeObject() {
        employeeRepository.save(employee);
        Employee employeeDB = employeeRepository.findById(employee.getId()).orElseThrow(() -> new RuntimeException("NOT_FOUND"));
        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.getId()).isEqualTo(employee.getId());
    }

    @Test
    @DisplayName("JUnit test for find employee by email operation")
    void givenEmployeeEmail_whenFindEmployeeByEmail_thenReturnEmployeeObject() {
        employeeRepository.save(employee);
        Employee employeeDB = employeeRepository.findEmployeeByEmail(employee.getEmail()).orElseThrow(() -> new RuntimeException("NOT_FOUND"));
        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.getEmail()).isEqualTo(employee.getEmail());
    }

    @Test
    @DisplayName("JUnit test for update employee operation")
    void givenEmployeeObject_whenUpdateEmployee_thenReturnEmployeeUpdated() {
        employeeRepository.save(employee);
        Employee employeeDB = employeeRepository.findById(employee.getId()).orElseThrow(() -> new RuntimeException("NOT_FOUND"));
        employeeDB.setFirstName("USER");
        employeeRepository.save(employeeDB);
        Employee employeeUpdated = employeeRepository.findById(employee.getId()).orElseThrow(() -> new RuntimeException("NOT_FOUND"));
        assertThat(employeeUpdated).isNotNull();
        assertThat(employeeUpdated.getFirstName()).isEqualTo("USER");
    }

    @Test
    @DisplayName("JUnit test for delete employee operation")
    void givenEmployeeObject_whenDeleteEmployee_thenEmployeeIsNoyFound() {
        employeeRepository.save(employee);
        employeeRepository.deleteById(employee.getId());
        assertThat(employeeRepository.findById(employee.getId())).isNotPresent();
    }

    @Test
    @DisplayName("JUnit test for find employee by firstName and lastName operation using index params")
    void givenFirstNameAndLastName_whenFindEmployeeByFirstNameAndLastNameIndexParams_thenReturnEmployeeObject() {
        employeeRepository.save(employee);
        Employee employeeDB = employeeRepository
                .findEmployeeByFirstNameAndLastNameIndexParams(
                        employee.getFirstName(),
                        employee.getLastName()
                ).orElseThrow(
                        () -> new RuntimeException("NOT_FOUND")
                );
        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.getFirstName()).isEqualTo("pbl");
        assertThat(employeeDB.getLastName()).isEqualTo("gllgs");
    }

    @Test
    @DisplayName("JUnit test for find employee by firstName and lastName operation using named params")
    void givenFirstNameAndLastName_whenFindEmployeeByFirstNameAndLastNameNamedParams_thenReturnEmployeeObject() {

        employeeRepository.save(employee);
        Employee employeeDB = employeeRepository
                .findEmployeeByFirstNameAndLastNameNamedParams(
                        employee.getFirstName(),
                        employee.getLastName()
                ).orElseThrow(
                        () -> new RuntimeException("NOT_FOUND")
                );
        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.getFirstName()).isEqualTo("pbl");
        assertThat(employeeDB.getLastName()).isEqualTo("gllgs");
    }

    @Test
    @DisplayName("JUnit test for find employee by firstName and lastName operation using index params with native sql")
    void givenFirstNameAndLastName_whenFindEmployeeByFirstNameAndLastNameIndexParamsNative_thenReturnEmployeeObject() {
        employeeRepository.save(employee);
        Employee employeeDB = employeeRepository
                .findEmployeeByFirstNameAndLastNameIndexParamsNative(
                        employee.getFirstName(),
                        employee.getLastName()
                ).orElseThrow(
                        () -> new RuntimeException("NOT_FOUND")
                );
        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.getFirstName()).isEqualTo("pbl");
        assertThat(employeeDB.getLastName()).isEqualTo("gllgs");
    }

    @Test
    @DisplayName("JUnit test for find employee by firstName and lastName operation using named params with native sql")
    void givenFirstNameAndLastName_whenFindEmployeeByFirstNameAndLastNameNamedParamsNative_thenReturnEmployeeObject() {
        employeeRepository.save(employee);
        Employee employeeDB = employeeRepository
                .findEmployeeByFirstNameAndLastNameNamedParamsNative(
                        employee.getFirstName(),
                        employee.getLastName()
                ).orElseThrow(
                        () -> new RuntimeException("NOT_FOUND")
                );
        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.getFirstName()).isEqualTo("pbl");
        assertThat(employeeDB.getLastName()).isEqualTo("gllgs");
    }
}