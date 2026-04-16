package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.CustomerDTO;
import com.classicmodels.classicmodels.dto.OrderDTO;
import com.classicmodels.classicmodels.dto.PaymentDTO;
import com.classicmodels.classicmodels.entity.Customer;
import com.classicmodels.classicmodels.entity.Employee;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.mapper.EntityMapper;
import com.classicmodels.classicmodels.repository.CustomerRepository;
import com.classicmodels.classicmodels.repository.EmployeeRepository;
import com.classicmodels.classicmodels.repository.OrderRepository;
import com.classicmodels.classicmodels.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final EntityMapper mapper;

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(mapper::toCustomerDTO)
                .toList();
    }

    public CustomerDTO getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with id: " + id));
        return mapper.toCustomerDTO(customer);
    }

    public CustomerDTO createCustomer(Customer customer) {
        if (customerRepository.existsById(customer.getCustomerNumber())) {
            throw new RuntimeException(
                    "Customer already exists with number: " + customer.getCustomerNumber());
        }
        return mapper.toCustomerDTO(customerRepository.save(customer));
    }

    public CustomerDTO updateCustomer(Integer id, Customer updatedCustomer) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with id: " + id));

        existing.setCustomerName(updatedCustomer.getCustomerName());
        existing.setContactFirstName(updatedCustomer.getContactFirstName());
        existing.setContactLastName(updatedCustomer.getContactLastName());
        existing.setPhone(updatedCustomer.getPhone());
        existing.setAddressLine1(updatedCustomer.getAddressLine1());
        existing.setAddressLine2(updatedCustomer.getAddressLine2());
        existing.setCity(updatedCustomer.getCity());
        existing.setState(updatedCustomer.getState());
        existing.setPostalCode(updatedCustomer.getPostalCode());
        existing.setCountry(updatedCustomer.getCountry());
        existing.setCreditLimit(updatedCustomer.getCreditLimit());

        if (updatedCustomer.getSalesRep() != null
                && updatedCustomer.getSalesRep().getEmployeeNumber() != null) {
            Employee salesRep = employeeRepository
                    .findById(updatedCustomer.getSalesRep().getEmployeeNumber())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Employee not found with id: "
                                    + updatedCustomer.getSalesRep().getEmployeeNumber()));
            existing.setSalesRep(salesRep);
        } else {
            existing.setSalesRep(null);
        }

        return mapper.toCustomerDTO(customerRepository.save(existing));
    }

    public void deleteCustomer(Integer id) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with id: " + id));
        customerRepository.delete(existing);
    }

    public List<CustomerDTO> searchByName(String name) {
        return customerRepository.findByCustomerNameContainingIgnoreCase(name)
                .stream().map(mapper::toCustomerDTO).toList();
    }

    public List<CustomerDTO> getByCountry(String country) {
        return customerRepository.findByCountry(country)
                .stream().map(mapper::toCustomerDTO).toList();
    }

    public List<CustomerDTO> getByCity(String city) {
        return customerRepository.findByCity(city)
                .stream().map(mapper::toCustomerDTO).toList();
    }

    // PRD 3.8 — Get all orders for a specific customer
    public List<OrderDTO> getOrdersByCustomer(Integer id) {
        customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with id: " + id));
        return orderRepository.findByCustomer_CustomerNumber(id)
                .stream().map(mapper::toOrderDTO).toList();
    }

    // PRD 3.8 — Get all payments for a specific customer
    public List<PaymentDTO> getPaymentsByCustomer(Integer id) {
        customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with id: " + id));
        return paymentRepository.findByCustomer_CustomerNumber(id)
                .stream().map(mapper::toPaymentDTO).toList();
    }

    public List<String> getAllCountries() {
        return customerRepository.findDistinctCountries();
    }

    public List<String> getAllCities() {
        return customerRepository.findDistinctCities();
    }
}