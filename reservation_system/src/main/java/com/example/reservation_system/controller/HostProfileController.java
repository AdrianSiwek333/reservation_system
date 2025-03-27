package com.example.reservation_system.controller;

import com.example.reservation_system.entity.HostProfile;
import com.example.reservation_system.entity.Users;
import com.example.reservation_system.service.CountryService;
import com.example.reservation_system.service.HostProfileService;
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

@RequestMapping("/hostProfile")
@Controller
public class HostProfileController {

    private final CountryService countryService;
    private final UsersService usersService;
    private final HostProfileService hostProfileService;

    public HostProfileController(CountryService countryService, UsersService usersService, HostProfileService hostProfileService) {
        this.countryService = countryService;
        this.usersService = usersService;
        this.hostProfileService = hostProfileService;
    }

    @GetMapping("/")
    public String hostProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return "hostProfile";
        }

        String username = authentication.getName();
        Users user = usersService.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        HostProfile hostProfile = hostProfileService.findById(user.getUserId())
                .orElse(new HostProfile());

        model.addAttribute("profile", hostProfile);
        model.addAttribute("countries", countryService.findAll());

        return "hostProfile";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("profile") HostProfile hostProfile,
                         BindingResult bindingResult, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(bindingResult.hasErrors()) {
            model.addAttribute("profile", hostProfile);
            return "customerProfile";
        }

        if(authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }

        boolean isHost = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("HOST"));

        if(!isHost){
            return "redirect:/accessDenied";
        }

        String username = authentication.getName();
        Users user = usersService.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        hostProfile.setHostProfileId(user.getUserId());
        hostProfile.setUserId(user);

        hostProfileService.update(hostProfile);

        return "redirect:/hostProfile/";
    }

}
