package com.example.reservation_system.controller;

import com.example.reservation_system.entity.Property;
import com.example.reservation_system.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final PropertyService propertyService;

    public HomeController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/")
    public String home(Model model) {

        List<Property> featuredProperties = propertyService.findRandom6Properties();
        model.addAttribute("featuredProperties", featuredProperties);
        return "dashboard";
    }
}
