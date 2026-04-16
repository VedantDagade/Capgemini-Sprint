package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findByCustomerNameContainingIgnoreCase(String customerName);
    List<Customer> findByCountry(String country);
    List<Customer> findByCity(String city);

    List<Customer> findBySalesRep_EmployeeNumber(Integer employeeNumber);

    @Query("SELECT DISTINCT c.country FROM Customer c ORDER BY c.country")
    List<String> findDistinctCountries();

    @Query("SELECT DISTINCT c.city FROM Customer c ORDER BY c.city")
    List<String> findDistinctCities();
}