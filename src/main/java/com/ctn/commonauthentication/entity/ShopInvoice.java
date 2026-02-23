package com.ctn.commonauthentication.entity;

import com.ctn.commonauthentication.util.enums.ScheduleType;
import com.ctn.commonauthentication.util.ScheduleTypeConverter;
import com.ctn.commonauthentication.util.enums.ShopTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "shops")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShopInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopid")
    private int id;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "name")
    private String name;
    @Column(name = "matched_mail_template_suffix")
    private String mailTemplate;
    @Column(name = "stopped")
    private Boolean stop;
    @Column(name = "cancelled")
    private Boolean cancel;
    @Column(name = "shop_type")
    @Enumerated(EnumType.STRING)
    private ShopTypeEnum shopTypeEnum;
    @Column(name = "vacation_period")
    private String vacationPeriod;
    @Column(name = "regular_closing_day")
    private String closingDay;
    @Column(name = "holiday_matching")
    private Boolean holidayMatching;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "refferal")
    private Double refferal = 0.0;
    @Column(name = "introduction")
    private Double introduction = 0.0;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "shop_image_url")
    private String shopImageUrl;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "prefectures")
    private String prefectures;
    @Column(name = "municipalities")
    private String municipalities;
    @Column(name = "address")
    private String address;
    @Column(name = "cancellation_category")
    private String cancellationCategory;
    @Column(name = "appeal_statement")
    private String appealStatement;
    @Column(name = "business_hours")
    private String businessHours;
    @Column(name = "schedule_date")
    private LocalDateTime scheduleDate;

    @Column(name = "schedule_type")
    @Convert(converter = ScheduleTypeConverter.class)
    private ScheduleType scheduleType;

    @Column(name = "is_processed")
    private Boolean isProcessed = false;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserInvoice userInvoice;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "shopid")
    private List<AssessedInvoice> assessedList;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "shopInvoice")
    private List<BillingEmails> billingEmails;




}
