package com.example.reservation_system.controller;

import com.example.reservation_system.entity.CustomerProfile;
import com.example.reservation_system.entity.Users;
import com.example.reservation_system.entity.UsersType;
import com.example.reservation_system.service.CustomerProfileService;
import com.example.reservation_system.service.UsersService;
import com.example.reservation_system.service.UsersTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    private UsersService usersService;
    private CustomerProfileService customerProfileService;
    private UsersTypeService usersTypeService;

    public UsersController(UsersService usersService, CustomerProfileService customerProfileService,
                           UsersTypeService usersTypeService) {
        this.usersService = usersService;
        this.customerProfileService = customerProfileService;
        this.usersTypeService = usersTypeService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        List<UsersType> usersTypes = usersTypeService.findAllExceptAdmin();
        model.addAttribute("usersTypes", usersTypes);
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register")
    public String register(Users user, Model model) {

        Optional<Users> optionalUser = usersService.findUserByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            model.addAttribute("error", "User is already registered");
            model.addAttribute("user", new Users());
            model.addAttribute("usersTypes", usersTypeService.findAllExceptAdmin());
            return "register";
        }
        usersService.addNewUser(user);
        return "redirect:/";
    }

}
