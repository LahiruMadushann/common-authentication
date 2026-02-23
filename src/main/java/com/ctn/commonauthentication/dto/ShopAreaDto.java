package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.entity.ShopArea;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShopAreaDto {
    private Integer id;
    private String postalCode;
    @NotBlank
    private String prefectures;
    @NotBlank
    private String manicipalities;
    private Boolean wholePurchase = false;

    public ShopArea toShopArea(int shopid) {
        ShopArea shopArea = new ShopArea();
        if (id != null) {
            shopArea.setAreaId(id);
        }
        shopArea.setPostalCode(postalCode);
        shopArea.setPrefecture(prefectures);
        shopArea.setManicipalities(manicipalities);
        shopArea.setShopId(shopid);
        shopArea.setWholePurchase(wholePurchase);
        return shopArea;
    }
}

