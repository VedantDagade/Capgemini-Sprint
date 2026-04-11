package com.classicmodels.classicmodels.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DashboardStatsDTO {
    private Long totalCustomers;
    private Long totalEmployees;
    private Long totalOffices;
    private Long totalProducts;
    private Long totalOrders;
    private Long totalOrdersShipped;
    private Long totalOrdersInProcess;
    private Long totalOrdersOnHold;
    private Long totalOrdersCancelled;
    private Long totalOrdersDisputed;
    private Long totalPayments;
    private BigDecimal totalRevenue;       // sum of all payment amounts
    private Long lowStockProducts;         // products with quantityInStock < 100
}
