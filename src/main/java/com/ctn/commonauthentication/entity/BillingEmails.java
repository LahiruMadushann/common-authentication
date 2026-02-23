package com.ctn.commonauthentication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "billing_emails")
public class BillingEmails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "shopid", referencedColumnName = "shopid")
    private ShopInvoice shopInvoice;


    @Column(name = "email")
    private String email;
}
