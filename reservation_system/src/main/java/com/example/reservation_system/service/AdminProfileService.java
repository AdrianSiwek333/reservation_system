package com.example.reservation_system.service;

import com.example.reservation_system.entity.AdminProfile;
import com.example.reservation_system.repository.AdminProfileRepository;
import com.example.reservation_system.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminProfileService {

    private final AdminProfileRepository adminProfileRepository;
    private final UsersRepository usersRepository;

    public AdminProfileService(AdminProfileRepository adminProfileRepository, UsersRepository usersRepository) {
        this.adminProfileRepository = adminProfileRepository;
        this.usersRepository = usersRepository;
    }

    public void update(AdminProfile adminProfile) {
        adminProfileRepository.save(adminProfile);
    }

    public Optional<AdminProfile> findById(int id) {
        return adminProfileRepository.findById(id);
    }
}
