package com.classicmodels.classicmodels.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productlines")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"products"})
@EqualsAndHashCode(of = "productLine")
public class ProductLine {

    @Id
    @Column(name = "productLine")
    private String productLine;

    @Column(name = "textDescription", length = 4000)
    private String textDescription;

    @Column(name = "htmlDescription", columnDefinition = "MEDIUMTEXT")
    private String htmlDescription;

    // One product line → many products
    @OneToMany(mappedBy = "productLineObj", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
}