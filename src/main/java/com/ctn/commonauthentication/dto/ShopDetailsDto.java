package com.ctn.commonauthentication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ShopDetailsDto {
    private Integer storeId;
    private String companyName;
    private String storeName;
    private String prefecture;
    private String municipality;

    public ShopDetailsDto(Integer storeId, String companyName, String storeName,
                          String prefecture, String municipality) {
        this.storeId = storeId;
        this.companyName = companyName;
        this.storeName = storeName;
        this.prefecture = prefecture;
        this.municipality = municipality;
    }
}
