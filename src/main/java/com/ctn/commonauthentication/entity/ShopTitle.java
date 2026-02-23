package com.ctn.commonauthentication.entity;

import java.time.LocalDateTime;

public class ShopTitle {

    private ShopID shopid;
    private String assessedEx;
    private String name;
    private String assessed_datetime;

    private boolean is_rejected_by_shop;
    private String assessed_status;
    private String reasonForEditPriceBuyer;
    private String reasonForEditPriceSeller;
    private Double adminPurchasePrice;
    private Double value;
    private Double finalOffer;

    private LocalDateTime adminPurchaseDateTime;
    private String adminEditReason;
    private Boolean sendToAISystem;

    private LocalDateTime userPurchaseDateTime;
    private LocalDateTime storePurchaseDateTime;

    private ShopTitle() { }

    public ShopTitle(ShopID shopid, String name, boolean is_rejected_by_shop, String assessed_status,
                     String assessed_datetime, String reasonForEditPriceBuyer,
                     String reasonForEditPriceSeller, Double adminPurchasePrice,
                     LocalDateTime adminPurchaseDateTime, String adminEditReason, Boolean sendToAISystem, LocalDateTime userPurchaseDateTime, LocalDateTime storePurchaseDateTime) {
        this.shopid = shopid;
        this.name = name;
        this.is_rejected_by_shop = is_rejected_by_shop;
        this.assessed_status = assessed_status;
        this.assessed_datetime = assessed_datetime;
        this.reasonForEditPriceBuyer = reasonForEditPriceBuyer;
        this.reasonForEditPriceSeller = reasonForEditPriceSeller;
        this.adminPurchasePrice = adminPurchasePrice;
        this.adminPurchaseDateTime = adminPurchaseDateTime;
        this.adminEditReason = adminEditReason;
        this.sendToAISystem = sendToAISystem;
        this.userPurchaseDateTime = userPurchaseDateTime;
        this.storePurchaseDateTime = storePurchaseDateTime;
    }

    public ShopID id() {
        return this.shopid;
    }

    public ShopID getShopid() {
        return shopid;
    }

    public void setShopid(ShopID shopid) {
        this.shopid = shopid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_rejected_by_shop() {
        return is_rejected_by_shop;
    }

    public void setIs_rejected_by_shop(boolean is_rejected_by_shop) {
        this.is_rejected_by_shop = is_rejected_by_shop;
    }

    public String getAssessed_datetime() {
        return assessed_datetime;
    }

    public void setAssessed_datetime(String assessed_datetime) {
        this.assessed_datetime = assessed_datetime;
    }

    public String getAssessedEx() {
        return assessedEx;
    }

    public void setAssessedEx(String assessedEx) {
        this.assessedEx = assessedEx;
    }

    public String getAssessed_status() {
        return assessed_status;
    }

    public void setAssessed_status(String assessed_status) {
        this.assessed_status = assessed_status;
    }

    public String getReasonForEditPriceBuyer() {
        return reasonForEditPriceBuyer;
    }

    public void setReasonForEditPriceBuyer(String reasonForEditPriceBuyer) {
        this.reasonForEditPriceBuyer = reasonForEditPriceBuyer;
    }

    public String getReasonForEditPriceSeller() {
        return reasonForEditPriceSeller;
    }

    public void setReasonForEditPriceSeller(String reasonForEditPriceSeller) {
        this.reasonForEditPriceSeller = reasonForEditPriceSeller;
    }

    public Double getAdminPurchasePrice() {
        return adminPurchasePrice;
    }

    public void setAdminPurchasePrice(Double adminPurchasePrice) {
        this.adminPurchasePrice = adminPurchasePrice;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getFinalOffer() {
        return finalOffer;
    }

    public void setFinalOffer(Double finalOffer) {
        this.finalOffer = finalOffer;
    }

    public LocalDateTime getAdminPurchaseDateTime() {
        return adminPurchaseDateTime;
    }

    public void setAdminPurchaseDateTime(LocalDateTime adminPurchaseDateTime) {
        this.adminPurchaseDateTime = adminPurchaseDateTime;
    }

    public String getAdminEditReason() {
        return adminEditReason;
    }

    public void setAdminEditReason(String adminEditReason) {
        this.adminEditReason = adminEditReason;
    }

    public Boolean getSendToAISystem() {
        return sendToAISystem;
    }

    public void setSendToAISystem(Boolean sendToAISystem) {
        this.sendToAISystem = sendToAISystem;
    }

    public LocalDateTime getUserPurchaseDateTime() {
        return userPurchaseDateTime;
    }

    public void setUserPurchaseDateTime(LocalDateTime userPurchaseDateTime) {
        this.userPurchaseDateTime = userPurchaseDateTime;
    }

    public LocalDateTime getStorePurchaseDateTime() {
        return storePurchaseDateTime;
    }

    public void setStorePurchaseDateTime(LocalDateTime storePurchaseDateTime) {
        this.storePurchaseDateTime = storePurchaseDateTime;
    }
}
