package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.OfficeDTO;
import com.classicmodels.classicmodels.entity.Office;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.mapper.EntityMapper;
import com.classicmodels.classicmodels.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OfficeService {

    private final OfficeRepository officeRepository;
    private final EntityMapper mapper;

    public List<OfficeDTO> getAllOffices() {
        return officeRepository.findAll()
                .stream()
                .map(mapper::toOfficeDTO)
                .toList();
    }

    public OfficeDTO getOfficeByCode(String code) {
        Office office = officeRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Office not found with code: " + code));
        return mapper.toOfficeDTO(office);
    }

    public OfficeDTO createOffice(Office office) {
        if (officeRepository.existsById(office.getOfficeCode())) {
            throw new RuntimeException(
                    "Office already exists with code: " + office.getOfficeCode());
        }
        return mapper.toOfficeDTO(officeRepository.save(office));
    }

    public OfficeDTO updateOffice(String code, Office updatedOffice) {
        Office existing = officeRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Office not found with code: " + code));

        existing.setCity(updatedOffice.getCity());
        existing.setPhone(updatedOffice.getPhone());
        existing.setAddressLine1(updatedOffice.getAddressLine1());
        existing.setAddressLine2(updatedOffice.getAddressLine2());
        existing.setState(updatedOffice.getState());
        existing.setCountry(updatedOffice.getCountry());
        existing.setPostalCode(updatedOffice.getPostalCode());
        existing.setTerritory(updatedOffice.getTerritory());

        return mapper.toOfficeDTO(officeRepository.save(existing));
    }

    public void deleteOffice(String code) {
        Office existing = officeRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Office not found with code: " + code));
        officeRepository.delete(existing);
    }
}