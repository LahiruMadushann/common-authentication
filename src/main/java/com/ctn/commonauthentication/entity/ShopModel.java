package com.ctn.commonauthentication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "shops", indexes = {
        @Index(name = "idx_shops_stopped_cancelled", columnList = "stopped, cancelled")
})
public class ShopModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopid")
    private Long shopId;

    @Column(name = "company_name")
    private String companyName;

    private String name;

    @Column(name = "matched_mail_template_suffix")
    private String matchedMailTemplateSuffix;

    private Boolean stopped = false;

    private Boolean cancelled = false;

    @Column(name = "shop_type")
    private String shopType;

    @Column(name = "vacation_period")
    private String vacationPeriod;

    @Column(name = "regular_closing_day")
    private String regularClosingDay;

    @Column(name = "holiday_matching")
    private Boolean holidayMatching = false;

    @Column(name = "payment_method")
    private String paymentMethod = "DEBIT";

    private Long refferal = 0L;

    private Long introduction = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel user;

    @Column(name = "phone_number", length = 255)
    private String phoneNumber;

    @Column(name = "shop_image_url", length = 1020)
    private String shopImageUrl;

    @Column(name = "postal_code", length = 255)
    private String postalCode;

    @Column(length = 255)
    private String prefectures;

    @Column(length = 255)
    private String municipalities;

    @Column(length = 255)
    private String address;

    @Column(name = "cancellation_category", length = 255)
    private String cancellationCategory;

    @Column(name = "appeal_statement", length = 255)
    private String appealStatement;

    @Column(name = "business_hours", length = 255)
    private String businessHours;
}