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
public class MatchedShop {
    @JsonProperty("store_id")
    private Long shopId;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("store_name")
    private String shopName;

}
