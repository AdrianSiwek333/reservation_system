package com.example.reservation_system.controller;

import com.example.reservation_system.entity.CustomerProfile;
import com.example.reservation_system.entity.Users;
import com.example.reservation_system.service.CountryService;
import com.example.reservation_system.service.CustomerProfileService;
import com.example.reservation_system.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/customerProfile")
public class CustomerProfileController {

    private final CustomerProfileService customerProfileService;
    private final UsersService usersService;
    private final CountryService countryService;

    public CustomerProfileController(CustomerProfileService customerProfileService, UsersService usersService,
                                     CountryService countryService) {
        this.customerProfileService = customerProfileService;
        this.usersService = usersService;
        this.countryService = countryService;
    }

    @GetMapping("/")
    public String customerProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof AnonymousAuthenticationToken) {
            return "customerProfile";
        }

        String username = authentication.getName();
        Users user = usersService.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        CustomerProfile customerProfile = customerProfileService.findById(user.getUserId())
                .orElse(new CustomerProfile());

        model.addAttribute("profile", customerProfile);
        model.addAttribute("countries", countryService.findAll());

        return "customerProfile";
    }

    @PostMapping("/update")
    public String updateProfile(@Valid @ModelAttribute("profile") CustomerProfile customerProfile,
                                BindingResult bindingResult, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(bindingResult.hasErrors()) {
            model.addAttribute("profile", customerProfile);
            return "customerProfile";
        }

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }

        Users user = usersService.findUserByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        customerProfile.setUserId(user);
        customerProfile.setCustomerProfileId(user.getUserId());

        customerProfileService.addNew(customerProfile);

        return "redirect:/customerProfile/";
    }

}
