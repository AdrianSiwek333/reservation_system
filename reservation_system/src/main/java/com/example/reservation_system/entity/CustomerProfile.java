package com.example.reservation_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_profile")
public class CustomerProfile {

    @Id
    private int tempId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "customer_profile_id")
    private Users customerProfileId;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country")
    private Country country;

    public CustomerProfile() {
    }

    public CustomerProfile(int tempId, Users customerProfileId, String firstName, String lastName, String phoneNumber, Country country) {
        this.tempId = tempId;
        this.customerProfileId = customerProfileId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.country = country;
    }

    public int getTempId() {
        return tempId;
    }

    public void setTempId(int customerProfileId) {
        this.tempId = customerProfileId;
    }

    public Users getCustomerProfileId() {
        return customerProfileId;
    }

    public void setCustomerProfileId(Users userId) {
        this.customerProfileId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country countryId) {
        this.country = countryId;
    }

    @Override
    public String toString() {
        return "CustomerProfile{" +
                "customerProfileId=" + tempId +
                ", userId=" + customerProfileId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", countryId=" + country +
                '}';
    }
}
