package com.ctn.commonauthentication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "billing_data")
@Data
public class BillingDataInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "billing_address_zip_code")
    private String zipCode;
    @Column(name = "billing_prefecture")
    private String billingPrefecture;
    @Column(name = "billing_address_municipalities")
    private String billingMuncipalities;
    @Column(name = "billing_address")
    private String billingAddress;
    @Column(name = "billing_department")
    private String billingDepartment;
    @Column(name = "billing_person_in_charge")
    private String pic;
//    @Column(name = "introduction_fee")
//    private Double introductionFee;
//    @Column(name = "referral_fee")
//    private Double referralFee;
//    @Column(name = "payment_method")
//    @Enumerated(EnumType.STRING)
//    private PaymentType paymentType;

    @OneToOne
    @JoinColumn(name = "shopid")
    private ShopInvoice shopInvoice;

}
