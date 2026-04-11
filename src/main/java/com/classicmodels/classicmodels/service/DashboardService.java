package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.DashboardStatsDTO;
import com.classicmodels.classicmodels.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DashboardService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final OfficeRepository officeRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public DashboardStatsDTO getStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();

        stats.setTotalCustomers(customerRepository.count());
        stats.setTotalEmployees(employeeRepository.count());
        stats.setTotalOffices(officeRepository.count());
        stats.setTotalProducts(productRepository.count());
        stats.setTotalOrders(orderRepository.count());

        stats.setTotalOrdersShipped(orderRepository.countByStatus("Shipped"));
        stats.setTotalOrdersInProcess(orderRepository.countByStatus("In Process"));
        stats.setTotalOrdersOnHold(orderRepository.countByStatus("On Hold"));
        stats.setTotalOrdersCancelled(orderRepository.countByStatus("Cancelled"));
        stats.setTotalOrdersDisputed(orderRepository.countByStatus("Disputed"));

        stats.setTotalPayments(paymentRepository.count());
        stats.setTotalRevenue(paymentRepository.sumAllPaymentAmounts());

        // Products with quantityInStock < 100
        stats.setLowStockProducts(productRepository.countByQuantityInStockLessThan((short) 100));

        return stats;
    }
}
