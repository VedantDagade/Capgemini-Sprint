package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<Office, String> {

    // Filter by country
    List<Office> findByCountry(String country);

    // Filter by territory
    List<Office> findByTerritory(String territory);

    // Filter by city
    List<Office> findByCity(String city);

    // Search by city name
    List<Office> findByCityContainingIgnoreCase(String city);

    @Query("SELECT DISTINCT o.country FROM Office o ORDER BY o.country")
    List<String> findDistinctCountries();

    @Query("SELECT DISTINCT o.territory FROM Office o ORDER BY o.territory")
    List<String> findDistinctTerritories();
}