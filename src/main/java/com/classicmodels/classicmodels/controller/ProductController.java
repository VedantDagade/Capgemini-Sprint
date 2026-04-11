package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.dto.ProductDTO;
import com.classicmodels.classicmodels.entity.Product;
import com.classicmodels.classicmodels.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{code}")
    public ResponseEntity<ProductDTO> getProductByCode(@PathVariable String code) {
        return ResponseEntity.ok(productService.getProductByCode(code));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody Product product) {
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    @PutMapping("/{code}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable String code,
            @Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(code, product));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable String code) {
        productService.deleteProduct(code);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Product deleted successfully with code: " + code);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> getProductsByLine(@RequestParam String line) {
        return ResponseEntity.ok(productService.getProductsByLine(line));
    }

    // PRD 3.7 — Get products with stock below threshold (default: 100)
    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductDTO>> getLowStockProducts(
            @RequestParam(defaultValue = "100") Short threshold) {
        return ResponseEntity.ok(productService.getLowStockProducts(threshold));
    }
}