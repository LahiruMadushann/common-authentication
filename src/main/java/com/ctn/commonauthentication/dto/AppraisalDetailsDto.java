package com.ctn.commonauthentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppraisalDetailsDto {
    private String param;
    private String maker;
    private String model;
    private String year;
    private String mileage;
    private String grade;
    private String bodyType;
    private String bodyColor;
    private String zipCode;
    private String municipality;
    private String prefecture;
    private String drivableStatus;
    private String accidentHistory;
    private String desiredSaleDate;
}
