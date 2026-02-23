package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.entity.FinalOfferStores;
import com.ctn.commonauthentication.util.enums.ProcessingStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AIFinalOfferResponseDto {
    //    @JsonProperty("request_id")
//    private UUID requestId;

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("appraisal_unique_key")
    private String appraisalUniqueKey;

    @JsonProperty("status")
    private ProcessingStatus status;

    @JsonProperty("stores")
    private List<FinalOfferStores> stores;

    @JsonProperty("timestamp")
    private OffsetDateTime timestamp;
}
