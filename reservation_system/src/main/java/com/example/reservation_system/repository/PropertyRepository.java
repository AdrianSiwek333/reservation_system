package com.example.reservation_system.repository;

import com.example.reservation_system.entity.HostProfile;
import com.example.reservation_system.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Integer> {

    public List<Property> findByHostId(HostProfile host);
}
