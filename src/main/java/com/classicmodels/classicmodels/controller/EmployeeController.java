package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.dto.EmployeeDTO;
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

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.createEmployee(employee), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Integer id,
            @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Employee deleted successfully with id: " + id);
        return ResponseEntity.ok(response);
    }

    // changed: /search?lastName=x
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDTO>> searchByLastName(@RequestParam String lastName) {
        return ResponseEntity.ok(employeeService.searchByLastName(lastName));
    }

    // changed: /filter/jobtitle?title=VP Sales
    @GetMapping("/filter/jobtitle")
    public ResponseEntity<List<EmployeeDTO>> getByJobTitle(@RequestParam String title) {
        return ResponseEntity.ok(employeeService.getByJobTitle(title));
    }

    // changed: /filter/office?code=1
    @GetMapping("/filter/office")
    public ResponseEntity<List<EmployeeDTO>> getByOfficeCode(@RequestParam String code) {
        return ResponseEntity.ok(employeeService.getByOfficeCode(code));
    }

    // changed: /filter/manager?managerId=1056
    @GetMapping("/filter/manager")
    public ResponseEntity<List<EmployeeDTO>> getByManager(@RequestParam Integer managerId) {
        return ResponseEntity.ok(employeeService.getByManager(managerId));
    }
}