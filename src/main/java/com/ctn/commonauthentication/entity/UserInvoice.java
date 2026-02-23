package com.ctn.commonauthentication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer userId;
    @Column(name = "user_id")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "enable")
    private Timestamp enable;

    @OneToMany(mappedBy = "userInvoice", fetch = FetchType.LAZY)
    private List<ShopInvoice> shopInvoices;
}

