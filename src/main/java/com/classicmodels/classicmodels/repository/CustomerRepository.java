package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findByCustomerNameContainingIgnoreCase(String customerName);
    List<Customer> findByCountry(String country);
    List<Customer> findByCity(String city);

    List<Customer> findBySalesRep_EmployeeNumber(Integer employeeNumber);
}