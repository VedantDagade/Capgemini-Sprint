package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entity.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLineRepository extends JpaRepository<ProductLine, String> {
    // productLine name is the primary key (String)
    // findById() covers GET /productlines/{name}
}