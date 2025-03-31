package com.example.reservation_system.service;

import com.example.reservation_system.entity.Reservation;
import com.example.reservation_system.repository.PropertyRepository;
import com.example.reservation_system.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PropertyRepository propertyRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              PropertyRepository propertyRepository) {
        this.reservationRepository = reservationRepository;
        this.propertyRepository = propertyRepository;
    }

    public List<LocalDate> getAvailableDatesForProperty(int propertyId, int year, int month) {
        List<Reservation> reservations = reservationRepository.findByPropertyId(propertyRepository.findById(propertyId).get());
        Set<LocalDate> bookedDates = reservations.stream()
                .flatMap(reservation -> reservation.getDatesBeetween().stream())
                .collect(Collectors.toSet());

        LocalDate today = LocalDate.of(year, month, 1);
        List<LocalDate> availableDates = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            LocalDate date = today.plusDays(i);
            if (!bookedDates.contains(date)) {
                availableDates.add(date);
            }
        }
        return availableDates;
    }
}
