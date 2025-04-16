package com.example.reservation_system.repository;

import com.example.reservation_system.entity.Property;
import com.example.reservation_system.entity.Reservation;
import com.example.reservation_system.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByPropertyId(Property property);

    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.propertyId.propertyId = :propertyId " +
            "AND ((r.reservationStartDate BETWEEN :startDate AND :endDate) " +
            "OR (r.reservationEndDate BETWEEN :startDate AND :endDate) " +
            "OR (:startDate BETWEEN r.reservationStartDate AND r.reservationEndDate))")
    boolean existsByPropertyIdAndDateRange(@Param("propertyId") int propertyId,
                                           @Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);

    List<Reservation> findByCustomerId(Users user);

}
