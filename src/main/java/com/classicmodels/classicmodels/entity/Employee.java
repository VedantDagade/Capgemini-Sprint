package com.classicmodels.classicmodels.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"office", "manager", "subordinates", "customers"})
@EqualsAndHashCode(of = "employeeNumber")
public class Employee {

    @Id
    @Column(name = "employeeNumber")
    @NotNull(message = "Employee number is required")
    @Positive(message = "Employee number must be a positive number")
    private Integer employeeNumber;

    @Column(name = "lastName", nullable = false)
    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @Column(name = "firstName", nullable = false)
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @Column(name = "extension", nullable = false)
    @NotBlank(message = "Extension is required")
    @Pattern(regexp = "^x[0-9]{4}$", message = "Extension must be in format x followed by 4 digits (e.g. x5800)")
    private String extension;

    @Column(name = "email", nullable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @Column(name = "jobTitle", nullable = false)
    @NotBlank(message = "Job title is required")
    @Size(max = 50, message = "Job title must not exceed 50 characters")
    private String jobTitle;

    // Many employees → one office
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officeCode", nullable = false)
    private Office office;

    // Self-referential: many employees → one manager
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportsTo")
    private Employee manager;

    // Self-referential: one manager → many subordinates
    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<Employee> subordinates = new ArrayList<>();

    // One sales rep → many customers
    @OneToMany(mappedBy = "salesRep", fetch = FetchType.LAZY)
    private List<Customer> customers = new ArrayList<>();
}