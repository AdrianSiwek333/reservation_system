package com.example.reservation_system.repository;

import com.example.reservation_system.entity.HostProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostProfileRepository extends JpaRepository<HostProfile, Integer> {
}
