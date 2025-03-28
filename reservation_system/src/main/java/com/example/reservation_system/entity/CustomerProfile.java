package com.example.reservation_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_profile")
public class CustomerProfile {

    @Id
    private int customerProfileId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "customer_profile_id")
    private Users userId;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country")
    private Country country;

    public CustomerProfile() {
    }

    public CustomerProfile(Users userId) {
        this.userId = userId;
        this.customerProfileId = userId.getUserId();
    }

    public CustomerProfile(int tempId, Users customerProfileId, String firstName, String lastName, String phoneNumber, Country country) {
        this.customerProfileId = tempId;
        this.userId = customerProfileId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.country = country;
    }

        public int getCustomerProfileId() {
        return customerProfileId;
    }

    public void setCustomerProfileId(int customerProfileId) {
        this.customerProfileId = customerProfileId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
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
                "customerProfileId=" + customerProfileId +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", countryId=" + country +
                '}';
    }
}
