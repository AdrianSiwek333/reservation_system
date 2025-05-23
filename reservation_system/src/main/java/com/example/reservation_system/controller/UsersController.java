package com.example.reservation_system.controller;

import com.example.reservation_system.entity.Users;
import com.example.reservation_system.entity.UsersType;
import com.example.reservation_system.service.UsersService;
import com.example.reservation_system.service.UsersTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    private final UsersService usersService;
    private final UsersTypeService usersTypeService;

    public UsersController(UsersService usersService, UsersTypeService usersTypeService) {
        this.usersService = usersService;
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
    public String register(@ModelAttribute("user") Users user, BindingResult result, Model model) {

        Optional<Users> optionalUser = usersService.findUserByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            result.rejectValue("email", "email.exists", "Account with this email already exists");
            model.addAttribute("user", user);
            model.addAttribute("usersTypes", usersTypeService.findAllExceptAdmin());
            return "register";
        }
        usersService.addNewUser(user);
        return "redirect:/";
    }

}
