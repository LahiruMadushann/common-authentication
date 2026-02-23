package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.entity.ShopVacation;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopVacationDto {
    private Integer id;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Timestamp start;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Timestamp end;


    public ShopVacation toShopVacation(int shopId) {
        ShopVacation shopVacation = new ShopVacation();
        shopVacation.setShopId(shopId);
        shopVacation.setStartDate(new java.sql.Date(start.getTime()));
        shopVacation.setEndDate(new java.sql.Date(end.getTime()));
        return shopVacation;
    }
}