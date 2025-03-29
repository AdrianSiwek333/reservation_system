package com.example.reservation_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "property_images")
public class PropertyImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int propertyImageId;

    private String imageUrl;

    public PropertyImages(int propertyImageId, String imageUrl) {
        this.propertyImageId = propertyImageId;
        this.imageUrl = imageUrl;
    }

    public PropertyImages(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public PropertyImages() {}

    public int getPropertyImageId() {
        return propertyImageId;
    }

    public void setPropertyImageId(int propertyImageId) {
        this.propertyImageId = propertyImageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
