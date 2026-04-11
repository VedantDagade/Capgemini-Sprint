package com.classicmodels.classicmodels.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Product line name is required")
    @Size(max = 50, message = "Product line name must not exceed 50 characters")
    private String productLine;

    @Column(name = "textDescription", length = 4000)
    @Size(max = 4000, message = "Text description must not exceed 4000 characters")
    private String textDescription;

    @Column(name = "htmlDescription", columnDefinition = "MEDIUMTEXT")
    private String htmlDescription;

    // One product line → many products
    @OneToMany(mappedBy = "productLineObj", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
}