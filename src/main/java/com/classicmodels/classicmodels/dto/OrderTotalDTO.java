package com.classicmodels.classicmodels.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderTotalDTO {
    private Integer orderNumber;
    private String customerName;
    private String status;
    private BigDecimal totalValue;    // sum of (quantityOrdered × priceEach)
    private Integer totalItems;       // count of distinct order lines
    private Integer totalQuantity;    // sum of all quantityOrdered
}
