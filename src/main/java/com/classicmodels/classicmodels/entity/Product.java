package com.classicmodels.classicmodels.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"productLineObj", "orderDetails"})
@EqualsAndHashCode(of = "productCode")
public class Product {

    @Id
    @Column(name = "productCode")
    @NotBlank(message = "Product code is required")
    @Size(max = 15, message = "Product code must not exceed 15 characters")
    private String productCode;

    @Column(name = "productName", nullable = false)
    @NotBlank(message = "Product name is required")
    @Size(max = 70, message = "Product name must not exceed 70 characters")
    private String productName;

    @Column(name = "productScale", nullable = false)
    @NotBlank(message = "Product scale is required")
    @Size(max = 10, message = "Product scale must not exceed 10 characters")
    private String productScale;

    @Column(name = "productVendor", nullable = false)
    @NotBlank(message = "Product vendor is required")
    @Size(max = 50, message = "Product vendor must not exceed 50 characters")
    private String productVendor;

    @Column(name = "productDescription", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Product description is required")
    private String productDescription;

    @Column(name = "quantityInStock", nullable = false)
    @NotNull(message = "Quantity in stock is required")
    @Min(value = 0, message = "Quantity in stock must be 0 or more")
    private Short quantityInStock;

    @Column(name = "buyPrice", nullable = false)
    @NotNull(message = "Buy price is required")
    @DecimalMin(value = "0.01", message = "Buy price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Buy price must have at most 10 integer digits and 2 decimal places")
    private BigDecimal buyPrice;

    @Column(name = "MSRP", nullable = false)
    @NotNull(message = "MSRP is required")
    @DecimalMin(value = "0.01", message = "MSRP must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "MSRP must have at most 10 integer digits and 2 decimal places")
    private BigDecimal msrp;

    // Many products → one product line
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productLine", nullable = false)
    private ProductLine productLineObj;

    // One product → many order details
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();
}