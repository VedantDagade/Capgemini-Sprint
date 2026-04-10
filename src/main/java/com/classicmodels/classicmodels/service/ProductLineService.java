package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.ProductLineDTO;
import com.classicmodels.classicmodels.entity.ProductLine;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.mapper.EntityMapper;
import com.classicmodels.classicmodels.repository.ProductLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductLineService {

    private final ProductLineRepository productLineRepository;
    private final EntityMapper mapper;

    public List<ProductLineDTO> getAllProductLines() {
        return productLineRepository.findAll()
                .stream()
                .map(mapper::toProductLineDTO)
                .toList();
    }

    public ProductLineDTO getProductLineByName(String name) {
        ProductLine productLine = productLineRepository.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product line not found with name: " + name));
        return mapper.toProductLineDTO(productLine);
    }

    public ProductLineDTO createProductLine(ProductLine productLine) {
        if (productLineRepository.existsById(productLine.getProductLine())) {
            throw new RuntimeException(
                    "Product line already exists with name: " + productLine.getProductLine());
        }
        return mapper.toProductLineDTO(productLineRepository.save(productLine));
    }

    public ProductLineDTO updateProductLine(String name, ProductLine updatedProductLine) {
        ProductLine existing = productLineRepository.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product line not found with name: " + name));

        existing.setTextDescription(updatedProductLine.getTextDescription());
        existing.setHtmlDescription(updatedProductLine.getHtmlDescription());

        return mapper.toProductLineDTO(productLineRepository.save(existing));
    }

    public void deleteProductLine(String name) {
        ProductLine existing = productLineRepository.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product line not found with name: " + name));
        productLineRepository.delete(existing);
    }
}