package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.OrderDTO;
import com.classicmodels.classicmodels.entity.Order;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.mapper.EntityMapper;
import com.classicmodels.classicmodels.repository.CustomerRepository;
import com.classicmodels.classicmodels.repository.OrderRepository;
import com.classicmodels.classicmodels.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private EntityMapper mapper;

    @InjectMocks
    private OrderService orderService;

    @Test
    void getAllOrders_ShouldReturnList_WhenOrdersExist() {
        // ARRANGE
        Order order = new Order();
        OrderDTO dto = new OrderDTO();
        when(orderRepository.findAll()).thenReturn(List.of(order));
        when(mapper.toOrderDTO(order)).thenReturn(dto);

        // ACT
        List<OrderDTO> result = orderService.getAllOrders();

        // ASSERT
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getAllOrders_ShouldReturnEmptyList_WhenNoOrdersExist() {
        // ARRANGE
        when(orderRepository.findAll()).thenReturn(List.of());

        // ACT
        List<OrderDTO> result = orderService.getAllOrders();

        // ASSERT
        assertTrue(result.isEmpty());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getOrderById_ShouldReturnOrder_WhenFound() {
        // ARRANGE
        Order order = new Order();
        OrderDTO dto = new OrderDTO();
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(mapper.toOrderDTO(order)).thenReturn(dto);

        // ACT
        OrderDTO result = orderService.getOrderById(1);

        // ASSERT
        assertNotNull(result);
        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    void getOrderById_ShouldThrowException_WhenNotFound() {
        // ARRANGE
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(1));
        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    void createOrder_ShouldSaveAndReturnOrder_WhenValid() {
        // ARRANGE
        Order order = new Order();
        order.setOrderNumber(1);
        OrderDTO dto = new OrderDTO();
        when(orderRepository.existsById(1)).thenReturn(false);
        // Assuming minimal validation logic requires customer to be mock retrieved
        // Not providing complete nested mock unless required. If this fails, mock
        // customerRepository.
        when(orderRepository.save(order)).thenReturn(order);
        when(mapper.toOrderDTO(order)).thenReturn(dto);

        // ACT
        OrderDTO result = orderService.createOrder(order);

        // ASSERT
        assertNotNull(result);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void updateOrder_ShouldUpdateAndReturnOrder_WhenFound() {
        // ARRANGE
        Order existing = new Order();
        Order updated = new Order();
        OrderDTO dto = new OrderDTO();
        when(orderRepository.findById(1)).thenReturn(Optional.of(existing));
        when(orderRepository.save(existing)).thenReturn(existing);
        when(mapper.toOrderDTO(existing)).thenReturn(dto);

        // ACT
        OrderDTO result = orderService.updateOrder(1, updated);

        // ASSERT
        assertNotNull(result);
        verify(orderRepository, times(1)).save(existing);
    }

    @Test
    void updateOrder_ShouldThrowException_WhenMissing() {
        // ARRANGE
        Order updated = new Order();
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> orderService.updateOrder(1, updated));
    }

    @Test
    void deleteOrder_ShouldDeleteSuccessfully_WhenFound() {
        // ARRANGE
        Order order = new Order();
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        // ACT
        orderService.deleteOrder(1);

        // ASSERT
        verify(orderRepository, times(1)).delete(order);
    }

    @Test
    void deleteOrder_ShouldThrowException_WhenMissing() {
        // ARRANGE
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> orderService.deleteOrder(1));
    }

    @Test
    void getOrdersByStatus_ShouldReturnList_WhenOrdersExist() {
        // ARRANGE
        Order order = new Order();
        OrderDTO dto = new OrderDTO();
        when(orderRepository.findByStatus("Shipped")).thenReturn(List.of(order));
        when(mapper.toOrderDTO(order)).thenReturn(dto);

        // ACT
        List<OrderDTO> result = orderService.getOrdersByStatus("Shipped");

        // ASSERT
        assertFalse(result.isEmpty());
        verify(orderRepository, times(1)).findByStatus("Shipped");
    }

    @Test
    void getOrdersByCustomer_ShouldReturnList_WhenOrdersExist() {
        // ARRANGE
        Order order = new Order();
        OrderDTO dto = new OrderDTO();
        when(orderRepository.findByCustomer_CustomerNumber(101)).thenReturn(List.of(order));
        when(mapper.toOrderDTO(order)).thenReturn(dto);

        // ACT
        List<OrderDTO> result = orderService.getOrdersByCustomer(101);

        // ASSERT
        assertFalse(result.isEmpty());
        verify(orderRepository, times(1)).findByCustomer_CustomerNumber(101);
    }
}