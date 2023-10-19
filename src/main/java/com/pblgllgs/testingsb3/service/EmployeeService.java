package com.pblgllgs.testingsb3.service;

import com.pblgllgs.testingsb3.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Optional<Employee> findById(Long id);
    Employee updateEmployee(Employee employee);
    void deleteEmployee(Long id);
}
