package com.pblgllgs.testingsb3.repository;

import com.pblgllgs.testingsb3.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findEmployeeByEmail(String email);

    @Query("SELECT e FROM Employee e WHERE e.firstName=?1 and e.lastName=?2")
    Optional<Employee> findEmployeeByFirstNameAndLastNameIndexParams(String firstName, String lastName);

    @Query("SELECT e FROM Employee e WHERE e.firstName=:firstName and e.lastName=:lastName")
    Optional<Employee> findEmployeeByFirstNameAndLastNameNamedParams(
            @Param("firstName") String firstName,
            @Param("lastName")String lastName);

    @Query(value = "SELECT * FROM employees e WHERE e.first_name=?1 and e.last_name=?2", nativeQuery = true)
    Optional<Employee> findEmployeeByFirstNameAndLastNameIndexParamsNative(String firstName, String lastName);

    @Query(value = "SELECT * FROM employees e WHERE e.first_name=:firstName and e.last_name=:lastName", nativeQuery = true)
    Optional<Employee> findEmployeeByFirstNameAndLastNameNamedParamsNative(
            @Param("firstName") String firstName,
            @Param("lastName")String lastName);

}
