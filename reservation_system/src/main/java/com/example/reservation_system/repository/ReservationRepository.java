package com.example.reservation_system.repository;

import com.example.reservation_system.entity.Property;
import com.example.reservation_system.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByPropertyId(Property property);
}
