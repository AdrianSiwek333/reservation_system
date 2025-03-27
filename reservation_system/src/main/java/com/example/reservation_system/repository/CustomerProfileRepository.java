package com.example.reservation_system.repository;

import com.example.reservation_system.entity.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Integer> {
}
