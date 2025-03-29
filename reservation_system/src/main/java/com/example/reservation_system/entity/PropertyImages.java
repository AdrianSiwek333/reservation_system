package com.example.reservation_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "property_images")
public class PropertyImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int propertyImageId;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
}
