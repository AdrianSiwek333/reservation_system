package com.example.reservation_system.repository;

import com.example.reservation_system.entity.HostProfile;
import com.example.reservation_system.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Integer>, JpaSpecificationExecutor<Property> {

    List<Property> findByHostId(HostProfile host);

    @Query(value = "SELECT * from property ORDER BY rand() limit 6", nativeQuery = true)
    List<Property> findRandom6Properties();

    @Query("SELECT (p.dayPrice * :days) from Property p " +
            "where p.propertyId = :propertyId")
    int calculatePrice(@Param("propertyId") int propertyId,
                           @Param("days") int days);

    @Query("SELECT p FROM Property p WHERE (LOWER(p.propertyName) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(p.city) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "AND p.dayPrice >= :minPrice AND p.dayPrice <= :maxPrice")
    Page<Property> findByQueryAndPriceRange(String query, Integer minPrice, Integer maxPrice, Pageable pageable);

    @Query("SELECT p FROM Property p WHERE LOWER(p.propertyName) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(p.city) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(p.country.countryName) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Property> findByQuery(String query, Pageable pageable);

    @Query("SELECT p FROM Property p WHERE p.dayPrice >= :minPrice AND p.dayPrice <= :maxPrice")
    Page<Property> findByPriceRange(Integer minPrice, Integer maxPrice, Pageable pageable);
}
