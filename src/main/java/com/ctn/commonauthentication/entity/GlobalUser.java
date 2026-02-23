package com.ctn.commonauthentication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "global_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "prefecture")
    private String prefecture;

    @Column(name = "city")
    private String city;

    @Column(name = "token")
    private String token;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 50)
    private Role role;

    @Column(name = "revoked", nullable = false)
    private Boolean revoked = false;

    public enum Role {
        ADMIN,
        USER,
        BUYER
    }
}
