package com.ctn.commonauthentication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "shop_holidays")
@Data
public class ShopHoliday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "shopid")
    private int shopId;
    @Column(name = "week")
    private int week;
    @Column(name = "day")
    private String Day;
}
