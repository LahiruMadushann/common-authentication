package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.entity.ShopHoliday;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopHolidaysDto {
    private Integer id;
    private Integer week;
    @Pattern(regexp = "^(MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY|SUNDAY)$", message = "Invalid day")
    private String day;

    public ShopHoliday toShopHoliday(int shopId) {
        ShopHoliday shopHoliday = new ShopHoliday();
        shopHoliday.setShopId(shopId);
        shopHoliday.setWeek(week);
        shopHoliday.setDay(day);
        return shopHoliday;
    }
}
