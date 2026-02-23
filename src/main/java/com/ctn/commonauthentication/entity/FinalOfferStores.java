package com.ctn.commonauthentication.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinalOfferStores {

    @JsonProperty("store_id")
    private Integer storeId;

    @JsonProperty("resolved_amount")
    private Double resolvedAmount;

    @JsonProperty("resolved_priority")
    private Integer resolvedPriority;
}
