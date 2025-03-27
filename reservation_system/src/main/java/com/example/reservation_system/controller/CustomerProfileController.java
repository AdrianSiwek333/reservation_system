package com.example.reservation_system.controller;

import com.example.reservation_system.entity.Country;
import com.example.reservation_system.entity.CustomerProfile;
import com.example.reservation_system.entity.Users;
import com.example.reservation_system.service.CountryService;
import com.example.reservation_system.service.CustomerProfileService;
import com.example.reservation_system.service.UsersService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

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
        CustomerProfile customerProfile = new CustomerProfile();
        List<Country> countries = countryService.findAll();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersService.findUserByEmail(authentication.getName()).orElseThrow(()->
                    new RuntimeException("user not found"));
            Optional<CustomerProfile> tempProfile = customerProfileService.findById(user.getUserId());
            if(tempProfile.isPresent()) {
                customerProfile = tempProfile.get();
            }
            model.addAttribute("customerProfile", customerProfile);
            model.addAttribute("countries", countries);
        }
        return "customerProfile";
    }

    @PostMapping("/update")
    public String update(CustomerProfile customerProfile, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersService.findUserByEmail(authentication.getName()).orElseThrow(()->
                    new UsernameNotFoundException("user not found"));
            customerProfile.setUserId(user);
            customerProfile.setCustomerProfileId(user.getUserId());
            System.out.println(customerProfile);
            customerProfileService.addNew(customerProfile);
        }
        return "redirect:/customerProfile/";
    }
}
