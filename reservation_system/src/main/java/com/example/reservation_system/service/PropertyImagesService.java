package com.example.reservation_system.service;

import com.example.reservation_system.entity.PropertyImages;
import com.example.reservation_system.repository.PropertyImagesRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyImagesService {

    private final PropertyImagesRepository propertyImagesRepository;

    public PropertyImagesService(PropertyImagesRepository propertyImagesRepository) {
        this.propertyImagesRepository = propertyImagesRepository;
    }

    public Optional<PropertyImages> findById(int id) {
        return propertyImagesRepository.findById(id);
    }
}
