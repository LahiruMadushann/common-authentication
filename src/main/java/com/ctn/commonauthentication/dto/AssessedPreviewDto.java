package com.ctn.commonauthentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssessedPreviewDto {
    private Long appraisalid;
    private Integer shopid;
    private Timestamp email_sent_time;
    private Timestamp draft_email_sent_time;
    private boolean is_rejected_by_shop;
    private String assessedEx;
    private Timestamp updatedAt;
    private String status;
    private Float value;
    private String supplementary;

    private Double finalOffer;
    private Double starValue;
    private Double starSupport;
    private Double starRecommendation;
    private Boolean soldToBuyer;
    private String review;
    private String reasonForEditPriceBuyer;
    private String reasonForEditPriceSeller;

    private String adminEditReason;
    private LocalDateTime adminPurchaseDateTime;
    private Double adminPurchasePrice;
    private Boolean sendToAISystem;
    private LocalDateTime userPurchaseDateTime;
    private LocalDateTime storePurchaseDateTime;

    private List<AdminPrices> adminPrices;

    private List<AssessedMemoPreviewDto> memos;
}
