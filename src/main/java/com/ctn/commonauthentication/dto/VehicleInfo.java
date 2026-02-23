package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.util.enums.Accident;
import com.ctn.commonauthentication.util.enums.RunnableStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleInfo {
    @JsonProperty("drivable_status")
    private RunnableStatus runnable;

    @JsonProperty("accident_history")
    private Accident accident;

    @JsonProperty("desired_sale_date")
    private String desiredSaleDate;


}
