package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.entity.Employee;
import com.classicmodels.classicmodels.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // GET all employees
    // URL: GET http://localhost:8080/api/employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // GET one employee by ID
    // URL: GET http://localhost:8080/api/employees/1002
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(
            @PathVariable Integer id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    // CREATE new employee
    // URL: POST http://localhost:8080/api/employees
    @PostMapping
    public ResponseEntity<Employee> createEmployee(
            @Valid @RequestBody Employee employee) {
        Employee created = employeeService.createEmployee(employee);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // UPDATE employee
    // URL: PUT http://localhost:8080/api/employees/1002
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Integer id,
            @Valid @RequestBody Employee employee) {
        Employee updated = employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(updated);
    }

    // DELETE employee
    // URL: DELETE http://localhost:8080/api/employees/1002
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteEmployee(
            @PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        Map<String, String> response = new HashMap<>();
        response.put("message",
                "Employee deleted successfully with id: " + id);
        return ResponseEntity.ok(response);
    }

    // SEARCH by last name
    // URL: GET http://localhost:8080/api/employees/search?lastName=jones
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchByLastName(
            @RequestParam String lastName) {
        List<Employee> employees =
                employeeService.searchByLastName(lastName);
        return ResponseEntity.ok(employees);
    }

    // FILTER by job title
    // URL: GET http://localhost:8080/api/employees/jobTitle?jobTitle=Sales Rep
    @GetMapping("/jobTitle")
    public ResponseEntity<List<Employee>> getByJobTitle(
            @RequestParam String jobTitle) {
        List<Employee> employees =
                employeeService.getByJobTitle(jobTitle);
        return ResponseEntity.ok(employees);
    }

    // FILTER by office code
    // URL: GET http://localhost:8080/api/employees/office?officeCode=1
    @GetMapping("/office")
    public ResponseEntity<List<Employee>> getByOfficeCode(
            @RequestParam String officeCode) {
        List<Employee> employees =
                employeeService.getByOfficeCode(officeCode);
        return ResponseEntity.ok(employees);
    }

    // GET employees reporting to a manager
    // URL: GET http://localhost:8080/api/employees/manager?reportsTo=1002
    @GetMapping("/manager")
    public ResponseEntity<List<Employee>> getByManager(
            @RequestParam Integer reportsTo) {
        List<Employee> employees =
                employeeService.getByReportsTo(reportsTo);
        return ResponseEntity.ok(employees);
    }
}