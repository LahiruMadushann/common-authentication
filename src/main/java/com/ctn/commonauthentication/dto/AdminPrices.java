package com.ctn.commonauthentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminPrices {
    private String adminEditReason;
    private LocalDateTime adminPurchaseDateTime;
    private Double adminPurchasePrice;
    private Boolean sendToAISystem;
    private Integer shopid;
}
