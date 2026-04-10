package com.classicmodels.classicmodels.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CustomerDTO {
    private Integer customerNumber;
    private String customerName;
    private String contactLastName;
    private String contactFirstName;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private BigDecimal creditLimit;

    // only the ID and name of the sales rep, not the whole Employee object
    private Integer salesRepEmployeeNumber;
    private String salesRepName;
}