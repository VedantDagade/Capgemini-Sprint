package com.classicmodels.classicmodels.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.classicmodels.classicmodels.entity.Employee;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    // GET all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // GET one employee by ID
    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id: " + id));
    }

    // CREATE new employee
    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsById(employee.getEmployeeNumber())) {
            throw new RuntimeException(
                    "Employee already exists with number: "
                            + employee.getEmployeeNumber());
        }
        return employeeRepository.save(employee);
    }

    // UPDATE existing employee
    public Employee updateEmployee(Integer id, Employee updatedEmployee) {
        Employee existing = getEmployeeById(id);

        existing.setLastName(updatedEmployee.getLastName());
        existing.setFirstName(updatedEmployee.getFirstName());
        existing.setExtension(updatedEmployee.getExtension());
        existing.setEmail(updatedEmployee.getEmail());
        existing.setOfficeCode(updatedEmployee.getOfficeCode());
        existing.setReportsTo(updatedEmployee.getReportsTo());
        existing.setJobTitle(updatedEmployee.getJobTitle());

        return employeeRepository.save(existing);
    }

    // DELETE employee
    public void deleteEmployee(Integer id) {
        Employee existing = getEmployeeById(id);
        employeeRepository.delete(existing);
    }

    // SEARCH by last name
    public List<Employee> searchByLastName(String lastName) {
        return employeeRepository
                .findByLastNameContainingIgnoreCase(lastName);
    }

    // FILTER by job title
    public List<Employee> getByJobTitle(String jobTitle) {
        return employeeRepository.findByJobTitle(jobTitle);
    }

    // FILTER by office code
    public List<Employee> getByOfficeCode(String officeCode) {
        return employeeRepository.findByOfficeCode(officeCode);
    }

    // GET employees by manager
    public List<Employee> getByReportsTo(Integer reportsTo) {
        return employeeRepository.findByReportsTo(reportsTo);
    }
}