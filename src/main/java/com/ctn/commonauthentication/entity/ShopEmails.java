package com.ctn.commonauthentication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "billing_emails")
public class ShopEmails {
    @Id
    @Column(name = "shopid")
    private Integer shopId;
    @Column(name = "email")
    private String email;
}
