package com.ctn.commonauthentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinalOfferShopDetailsDto {
    private Timestamp finalOfferCreatedAt;
    private Timestamp finalOfferUpdatedAt;
    private Timestamp valueCreatedAt;
    private Timestamp valueUpdatedAt;
}
