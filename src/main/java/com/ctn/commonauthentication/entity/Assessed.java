package com.ctn.commonauthentication.entity;

import com.ctn.commonauthentication.util.enums.EmailStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "assessed")
@AllArgsConstructor
@Data
public class Assessed {
    @EmbeddedId
    private AssessedId id;
    private Timestamp email_sent_time;
    private Timestamp draft_email_sent_time;
    private boolean is_rejected_by_shop;

    @Column(name = "assessed_ex")
    private String assessedEx;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "assessed_status")
    private String status;

    @Column(name = "evidence_image")
    private String evidenceImage;

    @Column(name = "reason_for_rejection")
    private String reasonForRejection;

    @Column(name = "application_date")
    private Timestamp applicationDate;

    @Column(name = "approval_date")
    private Timestamp approvalDate;

    @Column(name = "dismissal_date")
    private Timestamp dismissalDate;

    @Column(name = "buyer_reject")
    private Boolean buyerReject;

    @Column(name = "value")
    private Float value;

    @Column(name = "supplementary")
    private String supplementary;

    @Column(name = "final_offer")
    private Double finalOffer;

    @Column(name = "star_value")
    private Double starValue;

    @Column(name = "star_support")
    private Double starSupport;

    @Column(name = "star_recommendation")
    private Double starRecommendation;

    @Column(name = "sold_to_buyer")
    private Boolean soldToBuyer;

    @Column(name = "review")
    private String review;

    @Column(name = "email_type", columnDefinition = "emailstatus")
    @Enumerated(EnumType.STRING)
    private EmailStatus emailStatus;

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


    public Assessed() {
    }

    public Assessed(Long appraisalid, Integer shopid, Timestamp email_sent_time, boolean is_rejected_by_shop, String assessedEx, Timestamp updatedAt, String status) {
        this.id.setAppraisalid(appraisalid);
        this.id.setShopid(shopid);
        this.email_sent_time = email_sent_time;
        this.is_rejected_by_shop = is_rejected_by_shop;
        this.assessedEx = assessedEx;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public Assessed(Long appraisalid, Integer shopid, Timestamp email_sent_time, boolean is_rejected_by_shop, String assessedEx, Timestamp updatedAt) {
        this.id.setAppraisalid(appraisalid);
        this.id.setShopid(shopid);
        this.email_sent_time = email_sent_time;
        this.is_rejected_by_shop = is_rejected_by_shop;
        this.assessedEx = assessedEx;
        this.updatedAt = updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AssessedId getId() {
        return id;
    }

    public void setId(AssessedId id) {
        this.id = id;
    }

    public Timestamp getEmail_sent_time() {
        return email_sent_time;
    }

    public void setEmail_sent_time(Timestamp email_sent_time) {
        this.email_sent_time = email_sent_time;
    }

    public boolean isIs_rejected_by_shop() {
        return is_rejected_by_shop;
    }

    public Timestamp getDraft_email_sent_time() {
        return draft_email_sent_time;
    }

    public void setDraft_email_sent_time(Timestamp draft_email_sent_time) {
        this.draft_email_sent_time = draft_email_sent_time;
    }

    public void setIs_rejected_by_shop(boolean is_rejected_by_shop) {
        this.is_rejected_by_shop = is_rejected_by_shop;
    }

    public String getAssessed_ex() {
        return assessedEx;
    }

    public void setAssessed_ex(String assessed_ex) {
        this.assessedEx = assessedEx;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
