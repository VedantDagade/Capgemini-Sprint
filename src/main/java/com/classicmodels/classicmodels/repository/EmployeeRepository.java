package com.classicmodels.classicmodels.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.classicmodels.classicmodels.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByLastNameContainingIgnoreCase(String lastName);
    List<Employee> findByJobTitle(String jobTitle);

    // changed: was findByOfficeCode(String)
    List<Employee> findByOffice_OfficeCode(String officeCode);

    // changed: was findByReportsTo(Integer)
    List<Employee> findByManager_EmployeeNumber(Integer managerEmployeeNumber);
}