package com.classicmodels.classicmodels.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"salesRep", "orders", "payments"})
@EqualsAndHashCode(of = "customerNumber")
public class Customer {

    @Id
    @Column(name = "customerNumber")
    private Integer customerNumber;

    @Column(name = "customerName", nullable = false)
    private String customerName;

    @Column(name = "contactLastName", nullable = false)
    private String contactLastName;

    @Column(name = "contactFirstName", nullable = false)
    private String contactFirstName;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "addressLine1", nullable = false)
    private String addressLine1;

    @Column(name = "addressLine2")
    private String addressLine2;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "postalCode")
    private String postalCode;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "creditLimit")
    private BigDecimal creditLimit;

    // Many customers → one sales rep employee
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salesRepEmployeeNumber")
    private Employee salesRep;

    // One customer → many orders
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    // One customer → many payments
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();
}