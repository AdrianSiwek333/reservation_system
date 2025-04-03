package com.example.reservation_system.testRestControllers;

import com.example.reservation_system.entity.CustomerProfile;
import com.example.reservation_system.entity.Users;
import com.example.reservation_system.service.CustomerProfileService;
import com.example.reservation_system.service.UsersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/restUser")
public class UserRestController {

    private final UsersService usersService;
    private final CustomerProfileService customerProfileService;

    public UserRestController(UsersService usersService, CustomerProfileService customerProfileService) {
        this.usersService = usersService;
        this.customerProfileService = customerProfileService;
    }

    @GetMapping("/example")
    public Users example() {
        Optional<Users> userOptional = usersService.findUserById(1);

        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            System.out.println(user);
            return user;
        }

        return null;
    }

    @GetMapping("/customerProfile")
    public CustomerProfile customerProfile() {
        Optional<CustomerProfile> customerProfile = customerProfileService.findById(1);
        return customerProfile.orElse(null);
    }
}
