package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.CustomerDTO;
import com.classicmodels.classicmodels.entity.Customer;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.mapper.EntityMapper;
import com.classicmodels.classicmodels.repository.CustomerRepository;
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
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private EntityMapper mapper;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void getAllCustomers_ShouldReturnList_WhenCustomersExist() {
        // ARRANGE
        Customer customer = new Customer();
        CustomerDTO dto = new CustomerDTO();
        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(mapper.toCustomerDTO(customer)).thenReturn(dto);

        // ACT
        List<CustomerDTO> result = customerService.getAllCustomers();

        // ASSERT
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getAllCustomers_ShouldReturnEmptyList_WhenNoCustomersExist() {
        // ARRANGE
        when(customerRepository.findAll()).thenReturn(List.of());

        // ACT
        List<CustomerDTO> result = customerService.getAllCustomers();

        // ASSERT
        assertTrue(result.isEmpty());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomerById_ShouldReturnCustomer_WhenFound() {
        // ARRANGE
        Customer customer = new Customer();
        CustomerDTO dto = new CustomerDTO();
        when(customerRepository.findById(101)).thenReturn(Optional.of(customer));
        when(mapper.toCustomerDTO(customer)).thenReturn(dto);

        // ACT
        CustomerDTO result = customerService.getCustomerById(101);

        // ASSERT
        assertNotNull(result);
        verify(customerRepository, times(1)).findById(101);
    }

    @Test
    void getCustomerById_ShouldThrowException_WhenNotFound() {
        // ARRANGE
        when(customerRepository.findById(101)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(101));
        verify(customerRepository, times(1)).findById(101);
    }

    @Test
    void createCustomer_ShouldSaveAndReturnCustomer_WhenValid() {
        // ARRANGE
        Customer customer = new Customer();
        customer.setCustomerNumber(101);
        CustomerDTO dto = new CustomerDTO();
        when(customerRepository.existsById(101)).thenReturn(false);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(mapper.toCustomerDTO(customer)).thenReturn(dto);

        // ACT
        CustomerDTO result = customerService.createCustomer(customer);

        // ASSERT
        assertNotNull(result);
        verify(customerRepository, times(1)).save(customer);
    }
    
    @Test
    void updateCustomer_ShouldUpdateAndReturnCustomer_WhenFound() {
        // ARRANGE
        Customer existing = new Customer();
        Customer updated = new Customer();
        CustomerDTO dto = new CustomerDTO();
        when(customerRepository.findById(101)).thenReturn(Optional.of(existing));
        when(customerRepository.save(existing)).thenReturn(existing);
        when(mapper.toCustomerDTO(existing)).thenReturn(dto);

        // ACT
        CustomerDTO result = customerService.updateCustomer(101, updated);

        // ASSERT
        assertNotNull(result);
        verify(customerRepository, times(1)).save(existing);
    }

    @Test
    void updateCustomer_ShouldThrowException_WhenMissing() {
        // ARRANGE
        Customer updated = new Customer();
        when(customerRepository.findById(101)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> customerService.updateCustomer(101, updated));
    }

    @Test
    void deleteCustomer_ShouldDeleteSuccessfully_WhenFound() {
        // ARRANGE
        Customer customer = new Customer();
        when(customerRepository.findById(101)).thenReturn(Optional.of(customer));

        // ACT
        customerService.deleteCustomer(101);

        // ASSERT
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    void deleteCustomer_ShouldThrowException_WhenMissing() {
        // ARRANGE
        when(customerRepository.findById(101)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomer(101));
    }

    @Test
    void searchByName_ShouldReturnList_WhenCustomersExist() {
        // ARRANGE
        Customer customer = new Customer();
        CustomerDTO dto = new CustomerDTO();
        when(customerRepository.findByCustomerNameContainingIgnoreCase("Test")).thenReturn(List.of(customer));
        when(mapper.toCustomerDTO(customer)).thenReturn(dto);

        // ACT
        List<CustomerDTO> result = customerService.searchByName("Test");

        // ASSERT
        assertFalse(result.isEmpty());
        verify(customerRepository, times(1)).findByCustomerNameContainingIgnoreCase("Test");
    }
}
