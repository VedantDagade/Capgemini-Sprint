package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.dto.OfficeDTO;
import com.classicmodels.classicmodels.entity.Office;
import com.classicmodels.classicmodels.service.OfficeService;
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

@WebMvcTest(OfficeController.class)
public class OfficeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OfficeService officeService;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void getAllOffices_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        when(officeService.getAllOffices()).thenReturn(List.of(new OfficeDTO()));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/offices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
                
        verify(officeService, times(1)).getAllOffices();
    }

    @Test
    void getOfficeByCode_ShouldReturn200_WhenFound() throws Exception {
        // ARRANGE
        OfficeDTO dto = new OfficeDTO();
        dto.setCity("Boston");
        when(officeService.getOfficeByCode("1")).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/offices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Boston"));
                
        verify(officeService, times(1)).getOfficeByCode("1");
    }

    @Test
    void getOfficeByCode_ShouldReturn404_WhenNotFound() throws Exception {
        // ARRANGE
        when(officeService.getOfficeByCode("1")).thenThrow(new ResourceNotFoundException("Not found"));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/offices/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createOffice_ShouldReturn201_WhenValid() throws Exception {
        // ARRANGE
        Office office = new Office();
        office.setOfficeCode("1");
        office.setCity("Boston");
        office.setPhone("1234567890");
        office.setAddressLine1("Test");
        office.setCountry("Test");
        office.setPostalCode("12345");
        office.setTerritory("Test");
        OfficeDTO dto = new OfficeDTO();
        when(officeService.createOffice(any(Office.class))).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(post("/api/v1/offices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(office)))
                .andExpect(status().isCreated());
                
        verify(officeService, times(1)).createOffice(any(Office.class));
    }

    @Test
    void createOffice_ShouldReturn400_WhenInvalid() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(post("/api/v1/offices")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateOffice_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        Office office = new Office();
        office.setOfficeCode("1");
        office.setCity("Boston");
        office.setPhone("1234567890");
        office.setAddressLine1("Test");
        office.setCountry("Test");
        office.setPostalCode("12345");
        office.setTerritory("Test");
        OfficeDTO dto = new OfficeDTO();
        when(officeService.updateOffice(eq("1"), any(Office.class))).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(put("/api/v1/offices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(office)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOffice_ShouldReturn200_WhenFound() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(delete("/api/v1/offices/1"))
                .andExpect(status().isOk());
                
        verify(officeService, times(1)).deleteOffice("1");
    }
}