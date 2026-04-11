package com.classicmodels.classicmodels.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"customer"})
@EqualsAndHashCode(of = "id")
public class Payment {

    @EmbeddedId
    @NotNull(message = "Payment ID (customerNumber + checkNumber) is required")
    @Valid  // cascades validation into the embedded PaymentId fields
    private PaymentId id;

    @Column(name = "paymentDate", nullable = false)
    @NotNull(message = "Payment date is required")
    @PastOrPresent(message = "Payment date cannot be in the future")
    private LocalDate paymentDate;

    @Column(name = "amount", nullable = false)
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Payment amount must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Amount must have at most 10 integer digits and 2 decimal places")
    private BigDecimal amount;

    // Many payments → one customer
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("customerNumber")
    @JoinColumn(name = "customerNumber")
    private Customer customer;
}