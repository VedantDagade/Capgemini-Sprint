package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    // Filter by product line
    List<Product> findByProductLine(String productLine);

    // Search by product name (case insensitive)
    List<Product> findByProductNameContainingIgnoreCase(String productName);

    // Filter by vendor
    List<Product> findByProductVendor(String productVendor);
}