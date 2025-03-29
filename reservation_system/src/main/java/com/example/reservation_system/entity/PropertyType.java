package com.example.reservation_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "property_type")
public class PropertyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int propertyTypeId;

    private String typeName;

    public int getPropertyTypeId() {
        return propertyTypeId;
    }

    public void setPropertyTypeId(int propertyTypeId) {
        this.propertyTypeId = propertyTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public PropertyType() {}

    public PropertyType(int propertyTypeId, String typeName) {
        this.propertyTypeId = propertyTypeId;
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "PropertyType{" +
                "propertyTypeId=" + propertyTypeId +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
