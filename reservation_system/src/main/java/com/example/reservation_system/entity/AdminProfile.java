package com.example.reservation_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admin_profile")
public class AdminProfile {


    @Id
    private int adminProfileId;

    @OneToOne
    @JoinColumn(name = "admin_profile_id")
    @MapsId
    private Users userId;

    private String username;

    public AdminProfile() {}

    public AdminProfile(Users userId) {
        this.userId = userId;
        this.adminProfileId = userId.getUserId();
    }

    public AdminProfile(int adminProfileId, Users userId, String username) {
        this.adminProfileId = adminProfileId;
        this.userId = userId;
        this.username = username;
    }

    public int getAdminProfileId() {
        return adminProfileId;
    }

    public void setAdminProfileId(int adminProfileId) {
        this.adminProfileId = adminProfileId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "AdminProfile{" +
                "adminProfileId=" + adminProfileId +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                '}';
    }
}
