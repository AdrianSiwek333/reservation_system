package com.example.reservation_system.repository;

import com.example.reservation_system.entity.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyTypeRepository extends JpaRepository<PropertyType, Integer> {
}
