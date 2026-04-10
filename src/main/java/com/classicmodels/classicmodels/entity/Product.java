package com.classicmodels.classicmodels.entity;

import jakarta.persistence.*;
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
    private String productCode;

    @Column(name = "productName", nullable = false)
    private String productName;

    @Column(name = "productScale", nullable = false)
    private String productScale;

    @Column(name = "productVendor", nullable = false)
    private String productVendor;

    @Column(name = "productDescription", nullable = false, columnDefinition = "TEXT")
    private String productDescription;

    @Column(name = "quantityInStock", nullable = false)
    private Short quantityInStock;

    @Column(name = "buyPrice", nullable = false)
    private BigDecimal buyPrice;

    @Column(name = "MSRP", nullable = false)
    private BigDecimal msrp;

    // Many products → one product line
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productLine", nullable = false)
    private ProductLine productLineObj;

    // One product → many order details
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();
}