package com.classicmodels.classicmodels.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderDetailDTO {
    private Integer orderNumber;
    private String productCode;
    private String productName;
    private Integer quantityOrdered;
    private BigDecimal priceEach;
    private Short orderLineNumber;
}