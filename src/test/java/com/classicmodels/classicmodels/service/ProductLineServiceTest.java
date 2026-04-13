package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.ProductLineDTO;
import com.classicmodels.classicmodels.entity.ProductLine;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.mapper.EntityMapper;
import com.classicmodels.classicmodels.repository.ProductLineRepository;
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
public class ProductLineServiceTest {

    @Mock
    private ProductLineRepository productLineRepository;

    @Mock
    private EntityMapper mapper;

    @InjectMocks
    private ProductLineService productLineService;

    @Test
    void getAllProductLines_ShouldReturnList_WhenProductLinesExist() {
        // ARRANGE
        ProductLine productLine = new ProductLine();
        ProductLineDTO dto = new ProductLineDTO();
        when(productLineRepository.findAll()).thenReturn(List.of(productLine));
        when(mapper.toProductLineDTO(productLine)).thenReturn(dto);

        // ACT
        List<ProductLineDTO> result = productLineService.getAllProductLines();

        // ASSERT
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(productLineRepository, times(1)).findAll();
    }

    @Test
    void getAllProductLines_ShouldReturnEmptyList_WhenNoProductLinesExist() {
        // ARRANGE
        when(productLineRepository.findAll()).thenReturn(List.of());

        // ACT
        List<ProductLineDTO> result = productLineService.getAllProductLines();

        // ASSERT
        assertTrue(result.isEmpty());
        verify(productLineRepository, times(1)).findAll();
    }

    @Test
    void getProductLineByName_ShouldReturnProductLine_WhenFound() {
        // ARRANGE
        ProductLine productLine = new ProductLine();
        ProductLineDTO dto = new ProductLineDTO();
        when(productLineRepository.findById("Classic Cars")).thenReturn(Optional.of(productLine));
        when(mapper.toProductLineDTO(productLine)).thenReturn(dto);

        // ACT
        ProductLineDTO result = productLineService.getProductLineByName("Classic Cars");

        // ASSERT
        assertNotNull(result);
        verify(productLineRepository, times(1)).findById("Classic Cars");
    }

    @Test
    void getProductLineByName_ShouldThrowException_WhenNotFound() {
        // ARRANGE
        when(productLineRepository.findById("Unknown")).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> productLineService.getProductLineByName("Unknown"));
        verify(productLineRepository, times(1)).findById("Unknown");
    }

    @Test
    void createProductLine_ShouldSaveAndReturnProductLine_WhenValid() {
        // ARRANGE
        ProductLine productLine = new ProductLine();
        productLine.setProductLine("Classic Cars");
        ProductLineDTO dto = new ProductLineDTO();
        when(productLineRepository.existsById("Classic Cars")).thenReturn(false);
        when(productLineRepository.save(productLine)).thenReturn(productLine);
        when(mapper.toProductLineDTO(productLine)).thenReturn(dto);

        // ACT
        ProductLineDTO result = productLineService.createProductLine(productLine);

        // ASSERT
        assertNotNull(result);
        verify(productLineRepository, times(1)).save(productLine);
    }
    
    @Test
    void updateProductLine_ShouldUpdateAndReturnProductLine_WhenFound() {
        // ARRANGE
        ProductLine existing = new ProductLine();
        ProductLine updated = new ProductLine();
        ProductLineDTO dto = new ProductLineDTO();
        when(productLineRepository.findById("Classic Cars")).thenReturn(Optional.of(existing));
        when(productLineRepository.save(existing)).thenReturn(existing);
        when(mapper.toProductLineDTO(existing)).thenReturn(dto);

        // ACT
        ProductLineDTO result = productLineService.updateProductLine("Classic Cars", updated);

        // ASSERT
        assertNotNull(result);
        verify(productLineRepository, times(1)).save(existing);
    }

    @Test
    void updateProductLine_ShouldThrowException_WhenMissing() {
        // ARRANGE
        ProductLine updated = new ProductLine();
        when(productLineRepository.findById("Unknown")).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> productLineService.updateProductLine("Unknown", updated));
    }

    @Test
    void deleteProductLine_ShouldDeleteSuccessfully_WhenFound() {
        // ARRANGE
        ProductLine productLine = new ProductLine();
        when(productLineRepository.findById("Classic Cars")).thenReturn(Optional.of(productLine));

        // ACT
        productLineService.deleteProductLine("Classic Cars");

        // ASSERT
        verify(productLineRepository, times(1)).delete(productLine);
    }

    @Test
    void deleteProductLine_ShouldThrowException_WhenMissing() {
        // ARRANGE
        when(productLineRepository.findById("Unknown")).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> productLineService.deleteProductLine("Unknown"));
    }
}