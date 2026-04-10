package com.classicmodels.classicmodels.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orderdetails")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"order", "product"})
@EqualsAndHashCode(of = "id")
public class OrderDetail {

    @EmbeddedId
    private OrderDetailId id;

    @Column(name = "quantityOrdered", nullable = false)
    private Integer quantityOrdered;

    @Column(name = "priceEach", nullable = false)
    private BigDecimal priceEach;

    @Column(name = "orderLineNumber", nullable = false)
    private Short orderLineNumber;

    // Many order details → one order
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderNumber")
    @JoinColumn(name = "orderNumber")
    private Order order;

    // Many order details → one product
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productCode")
    @JoinColumn(name = "productCode")
    private Product product;
}