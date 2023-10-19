package com.pblgllgs.testingsb3.service.impl;

import com.pblgllgs.testingsb3.exception.EmployeeAlreadyExistsException;
import com.pblgllgs.testingsb3.exception.EmployeeNotExistsException;
import com.pblgllgs.testingsb3.model.Employee;
import com.pblgllgs.testingsb3.repository.EmployeeRepository;
import com.pblgllgs.testingsb3.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> employeeDB = employeeRepository.findEmployeeByEmail(employee.getEmail());
        if (employeeDB.isPresent()) {
            throw new EmployeeAlreadyExistsException("Employee already exists with id: " + employee.getId());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findById(Long id) {
        Optional<Employee> employeeDB = employeeRepository.findById(id);
        if (employeeDB.isEmpty()) {
            throw new EmployeeNotExistsException("Employee not exists with id: " + id);
        }
        return employeeDB;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
