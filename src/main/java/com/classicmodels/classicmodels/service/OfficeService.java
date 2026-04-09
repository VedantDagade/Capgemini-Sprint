package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entity.Office;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;

    // GET all offices
    public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }

    // GET one office by code
    public Office getOfficeByCode(String code) {
        return officeRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Office not found with code: " + code));
    }

    // CREATE new office
    public Office createOffice(Office office) {
        if (officeRepository.existsById(office.getOfficeCode())) {
            throw new RuntimeException(
                    "Office already exists with code: " + office.getOfficeCode());
        }
        return officeRepository.save(office);
    }

    // UPDATE existing office
    public Office updateOffice(String code, Office updatedOffice) {
        Office existing = getOfficeByCode(code);

        existing.setCity(updatedOffice.getCity());
        existing.setPhone(updatedOffice.getPhone());
        existing.setAddressLine1(updatedOffice.getAddressLine1());
        existing.setAddressLine2(updatedOffice.getAddressLine2());
        existing.setState(updatedOffice.getState());
        existing.setCountry(updatedOffice.getCountry());
        existing.setPostalCode(updatedOffice.getPostalCode());
        existing.setTerritory(updatedOffice.getTerritory());

        return officeRepository.save(existing);
    }

    // DELETE office
    public void deleteOffice(String code) {
        Office existing = getOfficeByCode(code);
        officeRepository.delete(existing);
    }
}