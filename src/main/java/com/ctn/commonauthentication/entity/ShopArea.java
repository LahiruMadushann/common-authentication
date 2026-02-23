package com.ctn.commonauthentication.entity;

import com.ctn.commonauthentication.dto.ShopAreaDto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "shop_area")
@IdClass(ShopAreaId.class)
public class ShopArea {
    @Id
    @Column(name = "areaid")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer areaId;
    @Id
    @Column(name = "shopid")
    private Integer shopId;
    @Column(name = "post_number")
    private String postalCode;
    @Column(name = "prefecture")
    private String prefecture;
    @Column(name = "municipalities")
    private String manicipalities;
    @Column(name = "whole_purchase")
    private Boolean wholePurchase;

    public ShopAreaDto toShopAreaDto() {
        ShopAreaDto shopAreaDto = new ShopAreaDto();
        shopAreaDto.setId(areaId);
        shopAreaDto.setPostalCode(postalCode);
        shopAreaDto.setPrefectures(prefecture);
        shopAreaDto.setManicipalities(manicipalities);
        shopAreaDto.setWholePurchase(wholePurchase);
        return shopAreaDto;
    }

}
