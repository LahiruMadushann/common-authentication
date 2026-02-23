package com.ctn.commonauthentication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreFinalOffer {
    @JsonProperty("store_id")
    private Integer storeId;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("store_name")
    private String storeName;

    @JsonProperty("prefecture")
    private String prefecture;

    @JsonProperty("municipality")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String municipality;

    @JsonProperty("quotes")
    private Quotes quotes;

    @JsonProperty("review")
    private Review review;

    @JsonProperty("purchase")
    private Purchase purchase;
}
