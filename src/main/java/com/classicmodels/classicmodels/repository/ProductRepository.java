package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    // changed: was findByProductLine(String)
    List<Product> findByProductLineObj_ProductLine(String productLine);

    List<Product> findByProductNameContainingIgnoreCase(String productName);
    List<Product> findByProductVendor(String productVendor);

    List<Product> findByQuantityInStockLessThan(Short threshold);
    Long countByQuantityInStockLessThan(Short threshold);
}