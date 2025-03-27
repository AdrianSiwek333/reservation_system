package com.example.reservation_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admin_profile")
public class AdminProfile {


    @Id
    private Integer adminProfileId;

    @OneToOne
    @JoinColumn(name = "user_id")
    @MapsId
    private Users userId;

    private String username;
}
