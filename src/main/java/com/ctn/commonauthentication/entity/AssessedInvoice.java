package com.ctn.commonauthentication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "assessed")
@Data
public class AssessedInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appraisalid")
    private BigInteger appraisalId;
    @Column(name = "shopid")
    private int shopId;
    @Column(name = "assessed_datetime")
    private Date assessedDateTime;
    @Column(name = "email_sent_time")
    private Date emailSendTime;
    @Column(name = "draft_email_sent_time")
    private Date draftEmailSendTime;
    @Column(name = "is_rejected_by_shop")
    private boolean reject;
    @Column(name = "assessed_ex")
    private String ex;
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "assessed_status")
    private String assessedStatus;
    @Column(name = "approval_date")
    private Timestamp approvalDate;

    @Column(name = "rejection_approval_date")
    private String rejectionApprovalDate;

    @Column(name = "is_increase_prefecture")
    private Boolean isIncreasePrefecture;

    @Column(name = "increase_prefecture_introduction")
    private Long increasePrefectureIntroduction;

    @Column(name = "reason_for_edit_price_buyer")
    private String reasonForEditPriceBuyer;

    @Column(name = "reason_for_edit_price_seller")
    private String reasonForEditPriceSeller;


    @Column(name = "admin_edit_reason")
    private String adminEditReason;

    @Column(name = "admin_purchase_date_time")
    private LocalDateTime adminPurchaseDateTime;

    @Column(name = "admin_purchase_price")
    private Double adminPurchasePrice;

    @Column(name = "send_to_ai_system")
    private Boolean sendToAISystem;

    @Column(name = "user_purchase_date_time")
    private LocalDateTime userPurchaseDateTime;

    @Column(name = "store_purchase_date_time")
    private LocalDateTime storePurchaseDateTime;

    //    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "shopid")
//    private ShopInvoice shopInvoice;
}
