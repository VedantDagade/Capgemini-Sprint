package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByStatus(String status);

    // changed: was findByCustomerNumber(Integer)
    List<Order> findByCustomer_CustomerNumber(Integer customerNumber);
}