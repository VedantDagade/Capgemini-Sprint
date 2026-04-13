package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.dto.OrderDTO;
import com.classicmodels.classicmodels.dto.OrderDetailDTO;
import com.classicmodels.classicmodels.dto.OrderTotalDTO;
import com.classicmodels.classicmodels.entity.Order;
import com.classicmodels.classicmodels.entity.OrderDetail;
import com.classicmodels.classicmodels.service.OrderService;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void getAllOrders_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        when(orderService.getAllOrders()).thenReturn(List.of(new OrderDTO()));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void getOrderById_ShouldReturn200_WhenFound() throws Exception {
        // ARRANGE
        OrderDTO dto = new OrderDTO();
        dto.setOrderNumber(1);
        when(orderService.getOrderById(1)).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber").value(1));

        verify(orderService, times(1)).getOrderById(1);
    }

    @Test
    void getOrderById_ShouldReturn404_WhenNotFound() throws Exception {
        // ARRANGE
        when(orderService.getOrderById(1)).thenThrow(new ResourceNotFoundException("Not found"));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/orders/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createOrder_ShouldReturn201_WhenValid() throws Exception {
        // ARRANGE
        Order order = new Order();
        order.setOrderNumber(1);
        order.setOrderDate(java.time.LocalDate.now());
        order.setRequiredDate(java.time.LocalDate.now().plusDays(5));
        order.setStatus("Shipped");
        OrderDTO dto = new OrderDTO();
        when(orderService.createOrder(any(Order.class))).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated());

        verify(orderService, times(1)).createOrder(any(Order.class));
    }

    @Test
    void createOrder_ShouldReturn400_WhenInvalid() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateOrder_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        Order order = new Order();
        order.setOrderNumber(1);
        order.setOrderDate(java.time.LocalDate.now());
        order.setRequiredDate(java.time.LocalDate.now().plusDays(5));
        order.setStatus("Shipped");
        OrderDTO dto = new OrderDTO();
        when(orderService.updateOrder(eq(1), any(Order.class))).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(put("/api/v1/orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOrder_ShouldReturn200_WhenFound() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(delete("/api/v1/orders/1"))
                .andExpect(status().isOk());

        verify(orderService, times(1)).deleteOrder(1);
    }

    @Test
    void getOrdersByStatus_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        when(orderService.getOrdersByStatus("Shipped")).thenReturn(List.of(new OrderDTO()));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/orders/search?status=Shipped"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getOrdersByCustomer_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        when(orderService.getOrdersByCustomer(101)).thenReturn(List.of(new OrderDTO()));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/orders/customer/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}