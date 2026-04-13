package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.dto.ProductDTO;
import com.classicmodels.classicmodels.entity.Product;
import com.classicmodels.classicmodels.service.ProductService;
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

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void getAllProducts_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        when(productService.getAllProducts()).thenReturn(List.of(new ProductDTO()));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
                
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void getProductByCode_ShouldReturn200_WhenFound() throws Exception {
        // ARRANGE
        ProductDTO dto = new ProductDTO();
        dto.setProductName("Test");
        when(productService.getProductByCode("P1")).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/products/P1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Test"));
                
        verify(productService, times(1)).getProductByCode("P1");
    }

    @Test
    void getProductByCode_ShouldReturn404_WhenNotFound() throws Exception {
        // ARRANGE
        when(productService.getProductByCode("P1")).thenThrow(new ResourceNotFoundException("Not found"));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/products/P1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createProduct_ShouldReturn201_WhenValid() throws Exception {
        // ARRANGE
        Product product = new Product();
        product.setProductCode("P1");
        product.setProductName("Test");
        product.setProductScale("1:10");
        product.setProductVendor("Test");
        product.setProductDescription("Test");
        product.setQuantityInStock((short)10);
        product.setBuyPrice(new java.math.BigDecimal("10.00"));
        product.setMsrp(new java.math.BigDecimal("10.00"));
        ProductDTO dto = new ProductDTO();
        when(productService.createProduct(any(Product.class))).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateProduct_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        Product product = new Product();
        product.setProductCode("P1");
        product.setProductName("Test");
        product.setProductScale("1:10");
        product.setProductVendor("Test");
        product.setProductDescription("Test");
        product.setQuantityInStock((short)10);
        product.setBuyPrice(new java.math.BigDecimal("10.00"));
        product.setMsrp(new java.math.BigDecimal("10.00"));
        ProductDTO dto = new ProductDTO();
        when(productService.updateProduct(eq("P1"), any(Product.class))).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(put("/api/v1/products/P1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProduct_ShouldReturn200_WhenFound() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(delete("/api/v1/products/P1"))
                .andExpect(status().isOk());
                
        verify(productService, times(1)).deleteProduct("P1");
    }

    @Test
    void getProductsByLine_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        when(productService.getProductsByLine("Ships")).thenReturn(List.of(new ProductDTO()));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/products/search?line=Ships"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
    
    @Test
    void getLowStockProducts_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        when(productService.getLowStockProducts((short) 100)).thenReturn(List.of(new ProductDTO()));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/products/low-stock?threshold=100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}