package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.OrderDTO;
import com.classicmodels.classicmodels.dto.OrderDetailDTO;
import com.classicmodels.classicmodels.entity.Customer;
import com.classicmodels.classicmodels.entity.Order;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.mapper.EntityMapper;
import com.classicmodels.classicmodels.repository.CustomerRepository;
import com.classicmodels.classicmodels.repository.OrderDetailRepository;
import com.classicmodels.classicmodels.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CustomerRepository customerRepository;
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
}