package com.ctn.commonauthentication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * One-to-one relationship with GlobalUser (auth account).
     * The foreign key (global_user_id) lives on this table.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "global_user_id", nullable = false, unique = true)
    private GlobalUser globalUser;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "prefecture", length = 50)
    private String prefecture;

    @Column(name = "city", length = 100)
    private String city;
}
