package com.example.reservation_system.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "property")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private int propertyId;

    private String propertyName;
    private String address;
    private String city;
    private String area;
    private String description;
    private boolean isActive;
    private int dayPrice;

    @ManyToOne
    @JoinColumn(name = "country")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "property_type_id")
    private PropertyType propertyTypeId;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private HostProfile hostId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "property_id")
    private List<PropertyImages> images;

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public PropertyType getPropertyTypeId() {
        return propertyTypeId;
    }

    public void setPropertyTypeId(PropertyType propertyTypeId) {
        this.propertyTypeId = propertyTypeId;
    }

    public int getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(int dayPrice) {
        this.dayPrice = dayPrice;
    }

    public List<PropertyImages> getImages() {
        return images;
    }

    public void setImages(List<PropertyImages> images) {
        this.images = images != null ? images : new ArrayList<>();
    }

    public HostProfile getHostId() {
        return hostId;
    }

    public void setHostId(HostProfile hostId) {
        this.hostId = hostId;
    }

    public String getEmptyPhoto(){
        return "/upload-dir/photos/property/empty.jpg";
    }

    public Property(){}

    public Property(int propertyId, String propertyName, String address, String city, String area, String description, boolean isActive, Country country, PropertyType propertyTypeId, HostProfile hostId, List<PropertyImages> images, int dayPrice) {
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.address = address;
        this.city = city;
        this.area = area;
        this.description = description;
        this.isActive = isActive;
        this.country = country;
        this.propertyTypeId = propertyTypeId;
        this.hostId = hostId;
        this.images = images;
        this.dayPrice = dayPrice;
    }

    public Property(int propertyId, String propertyName, String address, String city, String area, String description, boolean isActive, Country country, PropertyType propertyTypeId, HostProfile hostId, int dayPrice) {
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.address = address;
        this.city = city;
        this.area = area;
        this.description = description;
        this.isActive = isActive;
        this.country = country;
        this.propertyTypeId = propertyTypeId;
        this.hostId = hostId;
        this.images = new ArrayList<>();
        this.dayPrice = dayPrice;
    }

    @Override
    public String toString() {
        return "Property{" +
                "propertyId=" + propertyId +
                ", propertyName='" + propertyName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                ", dayPrice=" + dayPrice +
                ", country=" + country +
                ", propertyTypeId=" + propertyTypeId +
                ", hostId=" + hostId +
                ", images=" + images +
                '}';
    }

    public void addImage(PropertyImages image) {
        if(images == null){
            images = new ArrayList<>();
        }
        images.add(image);
    }

    public void removeImage(PropertyImages image) {
        this.images.remove(image);
    }
}
