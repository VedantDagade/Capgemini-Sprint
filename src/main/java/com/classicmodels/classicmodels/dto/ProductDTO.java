package com.classicmodels.classicmodels.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDTO {
    private String productCode;
    private String productName;
    private String productLine;
    private String productScale;
    private String productVendor;
    private String productDescription;
    private Short quantityInStock;
    private BigDecimal buyPrice;
    private BigDecimal msrp;
}