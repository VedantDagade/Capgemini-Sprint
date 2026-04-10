package com.classicmodels.classicmodels.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentDTO {
    private Integer customerNumber;
    private String customerName;
    private String checkNumber;
    private LocalDate paymentDate;
    private BigDecimal amount;
}