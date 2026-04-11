package com.classicmodels.classicmodels.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
    @NotNull(message = "Order detail ID (orderNumber + productCode) is required")
    @Valid  // cascades validation into the embedded OrderDetailId fields
    private OrderDetailId id;

    @Column(name = "quantityOrdered", nullable = false)
    @NotNull(message = "Quantity ordered is required")
    @Min(value = 1, message = "Quantity ordered must be at least 1")
    @Max(value = 10000, message = "Quantity ordered must not exceed 10000")
    private Integer quantityOrdered;

    @Column(name = "priceEach", nullable = false)
    @NotNull(message = "Price per item is required")
    @DecimalMin(value = "0.01", message = "Price per item must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must have at most 10 integer digits and 2 decimal places")
    private BigDecimal priceEach;

    @Column(name = "orderLineNumber", nullable = false)
    @NotNull(message = "Order line number is required")
    @Min(value = 1, message = "Order line number must be at least 1")
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