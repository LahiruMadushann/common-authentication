package com.ctn.commonauthentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppraisalData {
    @JsonProperty("unique_key")
    private String uniqueKey;

    @JsonProperty("maker")
    private String maker;

    @JsonProperty("model")
    private String model;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("mileage")
    private Integer mileage;

    @JsonProperty("grade")
    private String grade;

    @JsonProperty("body_type")
    private String bodyType;

    @JsonProperty("color")
    private String color;

    @JsonProperty("vehicle")
    private VehicleInfo vehicleInfo;
}
