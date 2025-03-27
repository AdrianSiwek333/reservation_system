package com.example.reservation_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "host_profile")
public class HostProfile {

    @Id
    private Integer hostProfileId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "host_profile_id")
    private Users userId;

    private String firstName;
    private String lastName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country")
    private Country country;

    private String phoneNumber;
    private String bankAccount;
    private String companyName;

    public HostProfile() {
    }

    public HostProfile(Users userId) {
        this.userId = userId;
        this.hostProfileId = userId.getUserId();
    }

    public HostProfile(Integer hostProfileId, Users userId, String firstName, String lastName, Country country, String phoneNumber, String bankAccount, String companyName) {
        this.hostProfileId = hostProfileId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.bankAccount = bankAccount;
        this.companyName = companyName;
    }

    public Integer getHostProfileId() {
        return hostProfileId;
    }

    public void setHostProfileId(Integer hostProfileId) {
        this.hostProfileId = hostProfileId;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country countryId) {
        this.country = countryId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "HostProfile{" +
                "hostProfileId=" + hostProfileId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", countryId=" + country +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", companyName='" + companyName + '\'' +
                ", userId=" + userId +
                '}';
    }
}
