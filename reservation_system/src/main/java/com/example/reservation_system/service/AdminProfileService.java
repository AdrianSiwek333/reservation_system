package com.example.reservation_system.service;

import com.example.reservation_system.entity.AdminProfile;
import com.example.reservation_system.repository.AdminProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminProfileService {

    private final AdminProfileRepository adminProfileRepository;

    public AdminProfileService(AdminProfileRepository adminProfileRepository) {
        this.adminProfileRepository = adminProfileRepository;
    }

    public void update(AdminProfile adminProfile) {
        adminProfileRepository.save(adminProfile);
    }

    public Optional<AdminProfile> findById(int id) {
        return adminProfileRepository.findById(id);
    }
}
