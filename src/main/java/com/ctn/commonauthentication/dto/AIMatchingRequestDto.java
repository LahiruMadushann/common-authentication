package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.util.enums.AreaType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AIMatchingRequestDto {
    @Builder.Default
    @JsonProperty("request_id")
    private String requestId = UUID.randomUUID().toString();

    @JsonProperty("appraisal")
    private AppraisalData appraisalData;

    @JsonProperty("matched_stores")
    private List<MatchedShop> matchedShops;

    @JsonProperty("matched_store_count")
    private Integer matchedStoreCount;

    @JsonProperty("area_type")
    private AreaType areaType;
}
