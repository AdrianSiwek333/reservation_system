package com.example.reservation_system.service;

import com.example.reservation_system.entity.*;
import com.example.reservation_system.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    private UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerProfileService customerProfileService;
    private final HostProfileService hostProfileService;
    private final AdminProfileService adminProfileService;

    public UsersService(UsersRepository usersRepository,
                        PasswordEncoder passwordEncoder,
                        CustomerProfileService customerProfileService,
                        HostProfileService hostProfileService,
                        AdminProfileService adminProfileService) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerProfileService = customerProfileService;
        this.hostProfileService = hostProfileService;
        this.adminProfileService = adminProfileService;
    }

    public Optional<Users> findUserById(int id) {
        return usersRepository.findById(id);
    }

    public Optional<Users> findUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public void addNewUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRegistrationDate(LocalDateTime.now());
        UsersType usersType = user.getUserTypeId();
        if(usersType.getUserTypeName().equals("ADMIN")){
            adminProfileService.update(new AdminProfile(user));
        }
        else if(usersType.getUserTypeName().equals("HOST")){
            hostProfileService.update(new HostProfile(user));
        }
        else{
            customerProfileService.addNew(new CustomerProfile(user));
        }
        usersRepository.save(user);
    }
}
