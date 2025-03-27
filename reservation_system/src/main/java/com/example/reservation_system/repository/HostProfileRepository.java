package com.example.reservation_system.repository;

import com.example.reservation_system.entity.HostProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostProfileRepository extends JpaRepository<HostProfile, Integer> {
}
