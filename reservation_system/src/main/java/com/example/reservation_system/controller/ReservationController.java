package com.example.reservation_system.controller;

import com.example.reservation_system.entity.Property;
import com.example.reservation_system.entity.Reservation;
import com.example.reservation_system.entity.Users;
import com.example.reservation_system.service.PropertyService;
import com.example.reservation_system.service.ReservationService;
import com.example.reservation_system.service.UsersService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final PropertyService propertyService;
    private final UsersService usersService;

    public ReservationController(ReservationService reservationService, PropertyService propertyService, UsersService usersService) {
        this.reservationService = reservationService;
        this.propertyService = propertyService;
        this.usersService = usersService;
    }

    @GetMapping("/new/{propertyId}")
    public String newReservation(@PathVariable int propertyId, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/accessDenied";
        }

        Property property = propertyService.findById(propertyId).orElseThrow(
                () -> new IllegalArgumentException("Property not found")
        );
        Reservation reservation = new Reservation();
        reservation.setPropertyId(property);
        model.addAttribute("reservation", reservation);

        return "reservation";
    }

    @PostMapping("/create")
    public String saveReservation(Reservation reservation, BindingResult result, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/accessDenied";
        }

        String username = authentication.getName();
        Users user = usersService.findUserByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        if(!reservationService.isAvailable(reservation.getPropertyId(), reservation.getReservationStartDate(),
                reservation.getReservationEndDate())) {
            result.reject("reservation_not_available", "this reservation dates are not available");
        }
        if(reservation.getReservationStartDate().isAfter(reservation.getReservationEndDate())
        || reservation.getReservationStartDate().isEqual(reservation.getReservationEndDate())) {
            result.rejectValue("reservationEndDate", "error.reservation" ,
                    "End date must be after start date");
        }
        if(result.hasErrors()) {
            model.addAttribute("reservation", reservation);
            return "reservation";
        }

        reservation.setStatus("Started");
        reservation.setCustomerId(user);
        reservation.setReservationCreationDate(LocalDate.now());
        reservation.setPrice(propertyService.calculatePrice(reservation.getPropertyId().getPropertyId(),
                reservation.getReservationStartDate(), reservation.getReservationEndDate()));
        reservationService.update(reservation);
        return "redirect:/";
    }
}
