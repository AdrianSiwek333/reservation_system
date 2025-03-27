package com.example.reservation_system.controller;

import com.example.reservation_system.entity.AdminProfile;
import com.example.reservation_system.entity.Country;
import com.example.reservation_system.entity.Users;
import com.example.reservation_system.service.AdminProfileService;
import com.example.reservation_system.service.CountryService;
import com.example.reservation_system.service.UsersService;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/adminProfile")
public class AdminProfileController {

    private final UsersService usersService;
    private final AdminProfileService adminProfileService;
    private final CountryService countryService;

    public AdminProfileController(UsersService usersService, AdminProfileService adminProfileService,
                                  CountryService countryService) {
        this.usersService = usersService;
        this.adminProfileService = adminProfileService;
        this.countryService = countryService;
    }

    @RequestMapping("/")
    public String adminProfile(Model model) {
        AdminProfile adminProfile = new AdminProfile();
        List<Country> countries = countryService.findAll();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersService.findUserByEmail(authentication.getName()).orElseThrow(()->
                    new UsernameNotFoundException("user not found"));
            Optional<AdminProfile> temp = adminProfileService.findById(user.getUserId());
            if(temp.isPresent()) {
                adminProfile = temp.get();
            }
            model.addAttribute("adminProfile", adminProfile);
            model.addAttribute("countries", countries);
        }

        return "adminProfile";

    }
}
