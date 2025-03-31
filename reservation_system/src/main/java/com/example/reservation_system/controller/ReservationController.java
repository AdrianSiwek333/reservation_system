package com.example.reservation_system.controller;

import com.example.reservation_system.entity.Property;
import com.example.reservation_system.entity.Reservation;
import com.example.reservation_system.service.CustomerProfileService;
import com.example.reservation_system.service.PropertyService;
import com.example.reservation_system.service.ReservationService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final CustomerProfileService customerProfileService;
    private final PropertyService propertyService;

    public ReservationController(ReservationService reservationService, CustomerProfileService customerProfileService, PropertyService propertyService) {
        this.reservationService = reservationService;
        this.customerProfileService = customerProfileService;
        this.propertyService = propertyService;
    }

    @GetMapping("/new/{propertyId}")
    public String newReservation(@PathVariable int propertyId, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/accessDenied";
        }
        //DODAJ WALIDACJE ROL


        Property property = propertyService.findById(propertyId).orElseThrow(
                () -> new IllegalArgumentException("Property not found")
        );
        Reservation reservation = new Reservation();
        reservation.setPropertyId(property);
        model.addAttribute("reservation", reservation);
        return "reservation";
    }
}
