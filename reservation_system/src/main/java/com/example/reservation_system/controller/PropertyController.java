package com.example.reservation_system.controller;

import com.example.reservation_system.entity.HostProfile;
import com.example.reservation_system.entity.Property;
import com.example.reservation_system.entity.Users;
import com.example.reservation_system.service.*;
import jakarta.validation.Valid;
import org.apache.catalina.Host;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/properties")
public class PropertyController {

    private final UsersService usersService;
    private final PropertyService propertyService;
    private final HostProfileService hostProfileService;
    private final PropertyTypeService propertyTypeService;
    private final CountryService countryService;

    public PropertyController(UsersService usersService, PropertyService propertyService,
                              HostProfileService hostProfileService, PropertyTypeService propertyTypeService, CountryService countryService) {
        this.usersService = usersService;
        this.propertyService = propertyService;
        this.hostProfileService = hostProfileService;
        this.propertyTypeService = propertyTypeService;
        this.countryService = countryService;
    }

    protected HostProfile getHostProfileIfHost(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        boolean isHost = authentication.getAuthorities().stream().anyMatch(
                authority -> authority.getAuthority().equals("HOST")
        );

        if(!isHost){
            return null;
        }

        String username = authentication.getName();
        Users user = usersService.findUserByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        return hostProfileService.findById(user.getUserId()).orElseThrow(
                () -> new UsernameNotFoundException("Host profile not found")
        );
    }

    @GetMapping("/yourProperties")
    public String yourProperties(Model model) {

        HostProfile hostProfile = getHostProfileIfHost();
        if(hostProfile == null){
            return "redirect:/accessDenied";
        }

        List<Property> properties = propertyService.findByHostProfile(hostProfile);
        model.addAttribute("properties", properties);

        return "hostProperties";
    }

    @GetMapping("/add")
    public String addProperty(Model model) {

        HostProfile hostProfile = getHostProfileIfHost();

        if(hostProfile == null){
            return "redirect:/accessDenied";
        }

        model.addAttribute("propertyType", propertyTypeService.findAll());
        model.addAttribute("property", new Property());
        model.addAttribute("countries", countryService.findAll());
        return "property";
    }

    @GetMapping("/edit/{id}")
    public String editProperty(@PathVariable("id") int id, Model model) {

        HostProfile hostProfile = getHostProfileIfHost();

        if(hostProfile == null){
            return "redirect:/accessDenied";
        }

        Property property = propertyService.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Property not found")
        );

        model.addAttribute("propertyType", propertyTypeService.findAll());
        model.addAttribute("property", property);
        model.addAttribute("countries", countryService.findAll());
        return "property";
    }

    @PostMapping("/update")
    public String updateProperty(@Valid @ModelAttribute("property") Property property,
                                 BindingResult bindingResult, Model model) {

        HostProfile hostProfile = getHostProfileIfHost();
        if(hostProfile == null){
            return "redirect:/accessDenied";
        }

        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("property", property);
            model.addAttribute("propertyType", propertyTypeService.findAll());
            model.addAttribute("countries", countryService.findAll());
            return "property";
        }

        property.setHostId(hostProfile);
        propertyService.update(property);
        return "redirect:/properties/yourProperties";
    }
}
