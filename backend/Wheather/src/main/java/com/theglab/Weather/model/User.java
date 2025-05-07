package com.theglab.Weather.model;

import jakarta.persistence.*;
@Entity
@Table(name = "users")
public class User {

    @Id
    private String email;

    @Column(nullable = false)
    private String role = "ROLE_USER";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
