package com.example.reservation_system.repository;

import com.example.reservation_system.entity.AdminProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminProfileRepository extends JpaRepository<AdminProfile, Integer> {
}
