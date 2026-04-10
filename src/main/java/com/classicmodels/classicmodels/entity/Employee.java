package com.classicmodels.classicmodels.entity;

import jakarta.persistence.*;
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
    private Integer employeeNumber;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "extension", nullable = false)
    private String extension;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "jobTitle", nullable = false)
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