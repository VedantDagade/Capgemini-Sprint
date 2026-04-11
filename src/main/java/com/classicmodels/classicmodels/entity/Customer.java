package com.classicmodels.classicmodels.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotNull(message = "Customer number is required")
    @Positive(message = "Customer number must be a positive number")
    private Integer customerNumber;

    @Column(name = "customerName", nullable = false)
    @NotBlank(message = "Customer name is required")
    @Size(max = 50, message = "Customer name must not exceed 50 characters")
    private String customerName;

    @Column(name = "contactLastName", nullable = false)
    @NotBlank(message = "Contact last name is required")
    @Size(max = 50, message = "Contact last name must not exceed 50 characters")
    private String contactLastName;

    @Column(name = "contactFirstName", nullable = false)
    @NotBlank(message = "Contact first name is required")
    @Size(max = 50, message = "Contact first name must not exceed 50 characters")
    private String contactFirstName;

    @Column(name = "phone", nullable = false)
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phone;

    @Column(name = "addressLine1", nullable = false)
    @NotBlank(message = "Address line 1 is required")
    @Size(max = 50, message = "Address line 1 must not exceed 50 characters")
    private String addressLine1;

    @Column(name = "addressLine2")
    @Size(max = 50, message = "Address line 2 must not exceed 50 characters")
    private String addressLine2;

    @Column(name = "city", nullable = false)
    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City must not exceed 50 characters")
    private String city;

    @Column(name = "state")
    @Size(max = 50, message = "State must not exceed 50 characters")
    private String state;

    @Column(name = "postalCode")
    @Size(max = 15, message = "Postal code must not exceed 15 characters")
    private String postalCode;

    @Column(name = "country", nullable = false)
    @NotBlank(message = "Country is required")
    @Size(max = 50, message = "Country must not exceed 50 characters")
    private String country;

    @Column(name = "creditLimit")
    @DecimalMin(value = "0.0", inclusive = true, message = "Credit limit must be 0 or greater")
    @Digits(integer = 10, fraction = 2, message = "Credit limit must have at most 10 integer digits and 2 decimal places")
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