package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.CustomerDTO;
import com.classicmodels.classicmodels.dto.EmployeeDTO;
import com.classicmodels.classicmodels.entity.Employee;
import com.classicmodels.classicmodels.entity.Office;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.mapper.EntityMapper;
import com.classicmodels.classicmodels.repository.CustomerRepository;
import com.classicmodels.classicmodels.repository.EmployeeRepository;
import com.classicmodels.classicmodels.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional  // THIS is what was missing
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final OfficeRepository officeRepository;
    private final CustomerRepository customerRepository;
    private final EntityMapper mapper;

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(mapper::toEmployeeDTO)
                .toList();
    }

    public EmployeeDTO getEmployeeById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id: " + id));
        return mapper.toEmployeeDTO(employee);
    }

    public EmployeeDTO createEmployee(Employee employee) {
        if (employeeRepository.existsById(employee.getEmployeeNumber())) {
            throw new RuntimeException(
                    "Employee already exists with number: " + employee.getEmployeeNumber());
        }
        return mapper.toEmployeeDTO(employeeRepository.save(employee));
    }

    public EmployeeDTO updateEmployee(Integer id, Employee updatedEmployee) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id: " + id));

        existing.setLastName(updatedEmployee.getLastName());
        existing.setFirstName(updatedEmployee.getFirstName());
        existing.setExtension(updatedEmployee.getExtension());
        existing.setEmail(updatedEmployee.getEmail());
        existing.setJobTitle(updatedEmployee.getJobTitle());

        if (updatedEmployee.getOffice() != null
                && updatedEmployee.getOffice().getOfficeCode() != null) {
            Office office = officeRepository
                    .findById(updatedEmployee.getOffice().getOfficeCode())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Office not found with code: "
                                    + updatedEmployee.getOffice().getOfficeCode()));
            existing.setOffice(office);
        }

        if (updatedEmployee.getManager() != null
                && updatedEmployee.getManager().getEmployeeNumber() != null) {
            Employee manager = employeeRepository
                    .findById(updatedEmployee.getManager().getEmployeeNumber())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Manager not found with id: "
                                    + updatedEmployee.getManager().getEmployeeNumber()));
            existing.setManager(manager);
        } else {
            existing.setManager(null);
        }

        return mapper.toEmployeeDTO(employeeRepository.save(existing));
    }

    public void deleteEmployee(Integer id) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id: " + id));
        employeeRepository.delete(existing);
    }

    public List<EmployeeDTO> searchByLastName(String lastName) {
        return employeeRepository.findByLastNameContainingIgnoreCase(lastName)
                .stream().map(mapper::toEmployeeDTO).toList();
    }

    public List<EmployeeDTO> getByJobTitle(String jobTitle) {
        return employeeRepository.findByJobTitle(jobTitle)
                .stream().map(mapper::toEmployeeDTO).toList();
    }

    public List<EmployeeDTO> getByOfficeCode(String officeCode) {
        return employeeRepository.findByOffice_OfficeCode(officeCode)
                .stream().map(mapper::toEmployeeDTO).toList();
    }

    public List<EmployeeDTO> getByManager(Integer managerEmployeeNumber) {
        return employeeRepository.findByManager_EmployeeNumber(managerEmployeeNumber)
                .stream().map(mapper::toEmployeeDTO).toList();
    }

    // PRD 3.9 — Get all customers managed by this employee as sales rep
    public List<CustomerDTO> getCustomersBySalesRep(Integer id) {
        employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id: " + id));
        return customerRepository.findBySalesRep_EmployeeNumber(id)
                .stream().map(mapper::toCustomerDTO).toList();
    }
}