package com.example.reservation_system.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int countryId;

    private String countryName;

    public Country() {
    }

    public Country(int countryId, String countryName, List<HostProfile> hostProfiles, List<CustomerProfile> customerProfiles) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryName='" + countryName + '\'' +
                ", countryId=" + countryId +
                '}';
    }
}
