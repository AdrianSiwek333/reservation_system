package com.example.reservation_system.controller;

import com.example.reservation_system.entity.AdminProfile;

import com.example.reservation_system.entity.Users;
import com.example.reservation_system.service.AdminProfileService;
import com.example.reservation_system.service.CountryService;
import com.example.reservation_system.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }

        String username = authentication.getName();
        Users user = usersService.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        AdminProfile adminProfile = adminProfileService.findById(user.getUserId())
                .orElseGet(() -> {
                    AdminProfile newProfile = new AdminProfile();
                    newProfile.setUserId(user);
                    adminProfileService.update(newProfile);
                    return newProfile;
                });

        model.addAttribute("profile", adminProfile);

        return "adminProfile";
    }

    @PostMapping("/update")
    public String adminProfileUpdate(@Valid @ModelAttribute("profile") AdminProfile adminProfile,
                                     BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("profile", adminProfile);
            return "adminProfile";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));

        if(!isAdmin){
            return "redirect:/accessDenied";
        }

        Users user = usersService.findUserByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        adminProfile.setUserId(user);
        adminProfile.setAdminProfileId(user.getUserId());

         adminProfileService.update(adminProfile);
        return "redirect:/adminProfile/";
    }

}
