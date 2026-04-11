package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.OrderDTO;
import com.classicmodels.classicmodels.dto.OrderDetailDTO;
import com.classicmodels.classicmodels.dto.OrderTotalDTO;
import com.classicmodels.classicmodels.entity.Customer;
import com.classicmodels.classicmodels.entity.Order;
import com.classicmodels.classicmodels.entity.OrderDetail;
import com.classicmodels.classicmodels.entity.Product;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.mapper.EntityMapper;
import com.classicmodels.classicmodels.repository.CustomerRepository;
import com.classicmodels.classicmodels.repository.OrderDetailRepository;
import com.classicmodels.classicmodels.repository.OrderRepository;
import com.classicmodels.classicmodels.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final EntityMapper mapper;

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::toOrderDTO)
                .toList();
    }

    public OrderDTO getOrderById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with id: " + id));
        return mapper.toOrderDTO(order);
    }

    public OrderDTO createOrder(Order order) {
        if (orderRepository.existsById(order.getOrderNumber())) {
            throw new RuntimeException(
                    "Order already exists with number: " + order.getOrderNumber());
        }

        // Credit limit check
        if (order.getCustomer() != null && order.getCustomer().getCustomerNumber() != null) {
            Customer customer = customerRepository
                    .findById(order.getCustomer().getCustomerNumber())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Customer not found with id: " + order.getCustomer().getCustomerNumber()));

            // Only check if credit limit is set (> 0 means limited credit)
            if (customer.getCreditLimit() != null
                    && customer.getCreditLimit().compareTo(BigDecimal.ZERO) > 0) {

                // Sum of all existing non-cancelled order details for this customer
                List<Order> existingOrders = orderRepository
                        .findByCustomer_CustomerNumber(customer.getCustomerNumber());

                BigDecimal outstanding = existingOrders.stream()
                        .filter(o -> !"Cancelled".equals(o.getStatus()))
                        .flatMap(o -> orderDetailRepository.findByIdOrderNumber(o.getOrderNumber()).stream())
                        .map(od -> od.getPriceEach().multiply(BigDecimal.valueOf(od.getQuantityOrdered())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                if (outstanding.compareTo(customer.getCreditLimit()) >= 0) {
                    throw new RuntimeException(
                            "Credit limit exceeded for customer: " + customer.getCustomerNumber()
                                    + ". Limit: " + customer.getCreditLimit()
                                    + ", Current outstanding: " + outstanding);
                }
            }
        }

        return mapper.toOrderDTO(orderRepository.save(order));
    }

    public OrderDTO updateOrder(Integer id, Order updatedOrder) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with id: " + id));

        existing.setOrderDate(updatedOrder.getOrderDate());
        existing.setRequiredDate(updatedOrder.getRequiredDate());
        existing.setShippedDate(updatedOrder.getShippedDate());
        existing.setStatus(updatedOrder.getStatus());
        existing.setComments(updatedOrder.getComments());

        if (updatedOrder.getCustomer() != null
                && updatedOrder.getCustomer().getCustomerNumber() != null) {
            Customer customer = customerRepository
                    .findById(updatedOrder.getCustomer().getCustomerNumber())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Customer not found with id: "
                                    + updatedOrder.getCustomer().getCustomerNumber()));
            existing.setCustomer(customer);
        }

        return mapper.toOrderDTO(orderRepository.save(existing));
    }

    public void deleteOrder(Integer id) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with id: " + id));
        orderRepository.delete(existing);
    }

    public List<OrderDTO> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status)
                .stream()
                .map(mapper::toOrderDTO)
                .toList();
    }

    public List<OrderDTO> getOrdersByCustomer(Integer customerId) {
        return orderRepository.findByCustomer_CustomerNumber(customerId)
                .stream()
                .map(mapper::toOrderDTO)
                .toList();
    }

    public List<OrderDetailDTO> getOrderDetails(Integer orderId) {
        orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with id: " + orderId));
        return orderDetailRepository.findByIdOrderNumber(orderId)
                .stream()
                .map(mapper::toOrderDetailDTO)
                .toList();
    }

    // PRD 3.4.1 — Add order detail with stock check + stock deduction
    public OrderDetailDTO addOrderDetail(Integer orderId, OrderDetail orderDetail) {
        // 1. Verify order exists
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with id: " + orderId));

        // 2. Verify product exists
        String productCode = orderDetail.getId().getProductCode();
        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with code: " + productCode));

        // 3. Check stock availability
        if (product.getQuantityInStock() < orderDetail.getQuantityOrdered()) {
            throw new RuntimeException(
                    "Insufficient stock for product: " + productCode
                            + ". Available: " + product.getQuantityInStock()
                            + ", Requested: " + orderDetail.getQuantityOrdered());
        }

        // 4. Link order and product, override orderId from path
        orderDetail.getId().setOrderNumber(orderId);
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);

        // 5. Save order detail
        OrderDetail saved = orderDetailRepository.save(orderDetail);

        // 6. Deduct stock and save updated product
        product.setQuantityInStock((short) (product.getQuantityInStock() - orderDetail.getQuantityOrdered()));
        productRepository.save(product);

        // 7. Return DTO
        return mapper.toOrderDetailDTO(saved);
    }

    // PRD 3.6 — Calculate order total
    public OrderTotalDTO getOrderTotal(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with id: " + orderId));

        List<OrderDetail> details = orderDetailRepository.findByIdOrderNumber(orderId);

        BigDecimal totalValue = details.stream()
                .map(od -> od.getPriceEach().multiply(BigDecimal.valueOf(od.getQuantityOrdered())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalQuantity = details.stream()
                .mapToInt(OrderDetail::getQuantityOrdered)
                .sum();

        OrderTotalDTO dto = new OrderTotalDTO();
        dto.setOrderNumber(order.getOrderNumber());
        dto.setCustomerName(order.getCustomer() != null ? order.getCustomer().getCustomerName() : null);
        dto.setStatus(order.getStatus());
        dto.setTotalValue(totalValue);
        dto.setTotalItems(details.size());
        dto.setTotalQuantity(totalQuantity);

        return dto;
    }
}