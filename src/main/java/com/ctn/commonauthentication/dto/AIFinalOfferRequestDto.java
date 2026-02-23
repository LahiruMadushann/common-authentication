package com.ctn.commonauthentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AIFinalOfferRequestDto {
    //    @Builder.Default
//    @JsonProperty("request_id")
//    private UUID requestId = UUID.randomUUID();

    @Builder.Default
    @JsonProperty("request_id")
    private String requestId = UUID.randomUUID().toString();

    @JsonProperty("appraisal")
    private AppraisalDataFinalOffer appraisal;

    @JsonProperty("stores")
    private List<StoreFinalOffer> stores;
}
