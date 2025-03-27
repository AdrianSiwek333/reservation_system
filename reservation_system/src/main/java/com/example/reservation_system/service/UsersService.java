package com.example.reservation_system.service;

import com.example.reservation_system.entity.Users;
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

    public UsersService(UsersRepository usersRepository,
                        PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Users> findUserById(int id) {
        return usersRepository.findById(id);
    }

    public void addNewUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRegistrationDate(LocalDateTime.now());
        usersRepository.save(user);
    }
}
