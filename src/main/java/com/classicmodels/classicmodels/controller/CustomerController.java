package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.dto.CustomerDTO;
import com.classicmodels.classicmodels.dto.OrderDTO;
import com.classicmodels.classicmodels.dto.PaymentDTO;
import com.classicmodels.classicmodels.entity.Customer;
import com.classicmodels.classicmodels.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable Integer id,
            @Valid @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Customer deleted successfully with id: " + id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(customerService.searchByName(name));
    }

    @GetMapping("/country")
    public ResponseEntity<List<CustomerDTO>> getByCountry(@RequestParam String country) {
        return ResponseEntity.ok(customerService.getByCountry(country));
    }

    @GetMapping("/city")
    public ResponseEntity<List<CustomerDTO>> getByCity(@RequestParam String city) {
        return ResponseEntity.ok(customerService.getByCity(city));
    }

    @GetMapping("/countries")
    public ResponseEntity<List<String>> getAllCountries() {
        return ResponseEntity.ok(customerService.getAllCountries());
    }

    @GetMapping("/cities")
    public ResponseEntity<List<String>> getAllCities() {
        return ResponseEntity.ok(customerService.getAllCities());
    }

    // PRD 3.8 — Get all orders for a specific customer
    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomer(@PathVariable Integer id) {
        return ResponseEntity.ok(customerService.getOrdersByCustomer(id));
    }

    // PRD 3.8 — Get all payments for a specific customer
    @GetMapping("/{id}/payments")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByCustomer(@PathVariable Integer id) {
        return ResponseEntity.ok(customerService.getPaymentsByCustomer(id));
    }
}