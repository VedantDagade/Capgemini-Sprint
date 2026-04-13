package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.EmployeeDTO;
import com.classicmodels.classicmodels.entity.Employee;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.mapper.EntityMapper;
import com.classicmodels.classicmodels.repository.EmployeeRepository;
import com.classicmodels.classicmodels.repository.OfficeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    
    @Mock
    private OfficeRepository officeRepository;

    @Mock
    private EntityMapper mapper;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void getAllEmployees_ShouldReturnList_WhenEmployeesExist() {
        // ARRANGE
        Employee employee = new Employee();
        EmployeeDTO dto = new EmployeeDTO();
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        when(mapper.toEmployeeDTO(employee)).thenReturn(dto);

        // ACT
        List<EmployeeDTO> result = employeeService.getAllEmployees();

        // ASSERT
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void getAllEmployees_ShouldReturnEmptyList_WhenNoEmployeesExist() {
        // ARRANGE
        when(employeeRepository.findAll()).thenReturn(List.of());

        // ACT
        List<EmployeeDTO> result = employeeService.getAllEmployees();

        // ASSERT
        assertTrue(result.isEmpty());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void getEmployeeById_ShouldReturnEmployee_WhenFound() {
        // ARRANGE
        Employee employee = new Employee();
        EmployeeDTO dto = new EmployeeDTO();
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(mapper.toEmployeeDTO(employee)).thenReturn(dto);

        // ACT
        EmployeeDTO result = employeeService.getEmployeeById(1);

        // ASSERT
        assertNotNull(result);
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void getEmployeeById_ShouldThrowException_WhenNotFound() {
        // ARRANGE
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(1));
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void createEmployee_ShouldSaveAndReturnEmployee_WhenValid() {
        // ARRANGE
        Employee employee = new Employee();
        employee.setEmployeeNumber(1);
        EmployeeDTO dto = new EmployeeDTO();
        when(employeeRepository.existsById(1)).thenReturn(false);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(mapper.toEmployeeDTO(employee)).thenReturn(dto);

        // ACT
        EmployeeDTO result = employeeService.createEmployee(employee);

        // ASSERT
        assertNotNull(result);
        verify(employeeRepository, times(1)).save(employee);
    }
    
    @Test
    void updateEmployee_ShouldUpdateAndReturnEmployee_WhenFound() {
        // ARRANGE
        Employee existing = new Employee();
        Employee updated = new Employee();
        EmployeeDTO dto = new EmployeeDTO();
        when(employeeRepository.findById(1)).thenReturn(Optional.of(existing));
        when(employeeRepository.save(existing)).thenReturn(existing);
        when(mapper.toEmployeeDTO(existing)).thenReturn(dto);

        // ACT
        EmployeeDTO result = employeeService.updateEmployee(1, updated);

        // ASSERT
        assertNotNull(result);
        verify(employeeRepository, times(1)).save(existing);
    }

    @Test
    void updateEmployee_ShouldThrowException_WhenMissing() {
        // ARRANGE
        Employee updated = new Employee();
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(1, updated));
    }

    @Test
    void deleteEmployee_ShouldDeleteSuccessfully_WhenFound() {
        // ARRANGE
        Employee employee = new Employee();
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        // ACT
        employeeService.deleteEmployee(1);

        // ASSERT
        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    void deleteEmployee_ShouldThrowException_WhenMissing() {
        // ARRANGE
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteEmployee(1));
    }

    @Test
    void getEmployeesByJobTitle_ShouldReturnList_WhenEmployeesExist() {
        // ARRANGE
        Employee employee = new Employee();
        EmployeeDTO dto = new EmployeeDTO();
        when(employeeRepository.findByJobTitle("SalesRep")).thenReturn(List.of(employee));
        when(mapper.toEmployeeDTO(employee)).thenReturn(dto);

        // ACT
        List<EmployeeDTO> result = employeeService.getByJobTitle("SalesRep");

        // ASSERT
        assertFalse(result.isEmpty());
        verify(employeeRepository, times(1)).findByJobTitle("SalesRep");
    }
}
