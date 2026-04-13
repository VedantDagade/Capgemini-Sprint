package com.classicmodels.classicmodels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailId implements Serializable {

    @Column(name = "orderNumber")
    @NotNull(message = "Order number is required")
    @Positive(message = "Order number must be a positive number")
    private Integer orderNumber;

    @Column(name = "productCode")
    @NotBlank(message = "Product code is required")
    @Size(max = 15, message = "Product code must not exceed 15 characters")
    private String productCode;

}