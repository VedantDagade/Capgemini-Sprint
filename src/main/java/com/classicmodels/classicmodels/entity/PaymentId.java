package com.classicmodels.classicmodels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor @AllArgsConstructor
public class PaymentId implements Serializable {

    @Column(name = "customerNumber")
    @NotNull(message = "Customer number is required in payment ID")
    @Positive(message = "Customer number must be a positive number")
    private Integer customerNumber;

    @Column(name = "checkNumber")
    @NotBlank(message = "Check number is required")
    @Size(max = 50, message = "Check number must not exceed 50 characters")
    private String checkNumber;
}