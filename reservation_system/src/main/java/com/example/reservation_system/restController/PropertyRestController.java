package com.example.reservation_system.restController;

import com.example.reservation_system.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PropertyRestController {

    private final ReservationService reservationService;

    public PropertyRestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{propertyId}/available-dates")
    public ResponseEntity<List<LocalDate>> getAvailableDate(@PathVariable("propertyId") int propertyId,
                                                             @RequestParam String month,
                                                             @RequestParam String year) {
        List<LocalDate> availableDates = reservationService.getAvailableDatesForProperty(propertyId, Integer.parseInt(year),
                Integer.parseInt(month));
        /*List<LocalDate> availableDates = reservationService.getAvailableDatesForProperty(propertyId, Integer.parseInt(year),
                Integer.parseInt(month));*/
        return ResponseEntity.ok(availableDates);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
