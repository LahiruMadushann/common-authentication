package com.ctn.commonauthentication.entity;

import com.ctn.commonauthentication.dto.ConditionRegistrationFormDto;
import com.ctn.commonauthentication.util.enums.SaleDateDiterminationType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "shop_handling_condition_general")
public class ShopHandlingConditionGeneral {
    @Id
    @Column(name = "shopid")
    private int shopid;

    @Column(name = "exclusivity")
    private String exclusivity;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "domestic_or_import")
    private Speciality.DomesticOrImport domesticOrImport;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "not_move")
    private Speciality.NotMove notMove;

    @Column(name = "car_year_ok_from")
    private String yearOkFrom;

    @Column(name = "car_year_ok_to")
    private String yearOkTo;

    @Column(name = "car_year_ng_from")
    private String yearNgFrom;

    @Column(name = "car_year_ng_to")
    private String yearNgTo;

    @Column(name = "car_traveled_distance_ok_from")
    private String distanceOkFrom;

    @Column(name = "car_traveled_distance_ok_to")
    private String distanceOkTo;

    @Column(name = "car_traveled_distance_ng_from")
    private String distanceNgFrom;

    @Column(name = "car_traveled_distance_ng_to")
    private String distanceNgTo;

    @Column(name = "shop_scale_rank")
    private Integer scaleRank = 1;

    @Column(name = "shop_ceil_match_count")
    private Integer ceilCount;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "sale_date_determination")
    private SaleDateDiterminationType saleDateDetermination = SaleDateDiterminationType.OK;


    public ConditionRegistrationFormDto toConditionRegistrationFormDto() {
        ConditionRegistrationFormDto conditionRegistrationFormDto = new ConditionRegistrationFormDto();
        conditionRegistrationFormDto.setShopId(shopid);
        conditionRegistrationFormDto.setDomesticOrImport(domesticOrImport);
        conditionRegistrationFormDto.setNotMove(notMove);
        conditionRegistrationFormDto.setYearOkFrom(yearOkFrom);
        conditionRegistrationFormDto.setYearOkTo(yearOkTo);
        conditionRegistrationFormDto.setYearNgFrom(yearNgFrom);
        conditionRegistrationFormDto.setYearNgTo(yearNgTo);
        conditionRegistrationFormDto.setDistanceOkFrom(distanceOkFrom);
        conditionRegistrationFormDto.setDistanceOkTo(distanceOkTo);
        conditionRegistrationFormDto.setDistanceNgFrom(distanceNgFrom);
        conditionRegistrationFormDto.setDistanceNgTo(distanceNgTo);
        conditionRegistrationFormDto.setScaleRank(scaleRank);
        conditionRegistrationFormDto.setCeilCount(ceilCount);
        conditionRegistrationFormDto.setSaleDateDetermination(saleDateDetermination);
        return conditionRegistrationFormDto;
    }
}
