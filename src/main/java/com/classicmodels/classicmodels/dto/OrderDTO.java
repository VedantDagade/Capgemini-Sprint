package com.classicmodels.classicmodels.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class OrderDTO {
    private Integer orderNumber;
    private LocalDate orderDate;
    private LocalDate requiredDate;
    private LocalDate shippedDate;
    private String status;
    private String comments;

    // only customer ID and name, not the whole Customer object
    private Integer customerNumber;
    private String customerName;
}