package com.example.reservation_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users_type")
public class UsersType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userTypeId;

    private String userTypeName;


    public UsersType() {
    }

    public UsersType(int userTypeId, String userTypeName) {
        this.userTypeId = userTypeId;
        this.userTypeName = userTypeName;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String user_type_name) {
        this.userTypeName = user_type_name;
    }


    @Override
    public String toString() {
        return "usersType{" +
                "userTypeId=" + userTypeId +
                ", user_type_name='" + userTypeName + '\'' +
                '}';
    }
}
