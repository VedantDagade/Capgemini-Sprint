package com.classicmodels.classicmodels.entity;

import jakarta.persistence.*;
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
    private PaymentId id;

    @Column(name = "paymentDate", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    // Many payments → one customer
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("customerNumber")
    @JoinColumn(name = "customerNumber")
    private Customer customer;
}