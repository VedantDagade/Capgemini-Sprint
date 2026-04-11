package com.classicmodels.classicmodels.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "offices")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"employees"})
@EqualsAndHashCode(of = "officeCode")
public class Office {

    @Id
    @Column(name = "officeCode")
    @NotBlank(message = "Office code is required")
    @Size(max = 10, message = "Office code must not exceed 10 characters")
    private String officeCode;

    @Column(name = "city", nullable = false)
    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City must not exceed 50 characters")
    private String city;

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

    @Column(name = "state")
    @Size(max = 50, message = "State must not exceed 50 characters")
    private String state;

    @Column(name = "country", nullable = false)
    @NotBlank(message = "Country is required")
    @Size(max = 50, message = "Country must not exceed 50 characters")
    private String country;

    @Column(name = "postalCode", nullable = false)
    @NotBlank(message = "Postal code is required")
    @Size(max = 15, message = "Postal code must not exceed 15 characters")
    private String postalCode;

    @Column(name = "territory", nullable = false)
    @NotBlank(message = "Territory is required")
    @Size(max = 10, message = "Territory must not exceed 10 characters")
    private String territory;

    // One office → many employees
    @OneToMany(mappedBy = "office", fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();
}