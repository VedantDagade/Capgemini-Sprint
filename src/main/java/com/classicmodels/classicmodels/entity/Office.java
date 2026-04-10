package com.classicmodels.classicmodels.entity;

import jakarta.persistence.*;
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
    private String officeCode;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "addressLine1", nullable = false)
    private String addressLine1;

    @Column(name = "addressLine2")
    private String addressLine2;

    @Column(name = "state")
    private String state;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "postalCode", nullable = false)
    private String postalCode;

    @Column(name = "territory", nullable = false)
    private String territory;

    // One office → many employees
    @OneToMany(mappedBy = "office", fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();
}