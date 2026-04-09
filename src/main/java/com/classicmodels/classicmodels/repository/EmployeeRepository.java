package com.classicmodels.classicmodels.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.classicmodels.classicmodels.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Search by last name
    List<Employee> findByLastNameContainingIgnoreCase(String lastName);

    // Filter by job title
    List<Employee> findByJobTitle(String jobTitle);

    // Filter by office code
    List<Employee> findByOfficeCode(String officeCode);

    // Find employees reporting to a manager
    List<Employee> findByReportsTo(Integer reportsTo);
}