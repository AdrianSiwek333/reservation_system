package com.example.reservation_system.service;

import com.example.reservation_system.entity.PropertyType;
import com.example.reservation_system.repository.PropertyTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyTypeService {

    private final PropertyTypeRepository propertyTypeRepository;

    public PropertyTypeService(PropertyTypeRepository propertyTypeRepository) {
        this.propertyTypeRepository = propertyTypeRepository;
    }

    public List<PropertyType> findAll() {
        return propertyTypeRepository.findAll();
    }
}
