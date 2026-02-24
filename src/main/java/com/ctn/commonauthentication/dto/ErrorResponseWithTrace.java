package com.ctn.commonauthentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ErrorResponseWithTrace extends ErrorResponse {

    @JsonProperty("trace_id")
    private String traceId;

    public ErrorResponseWithTrace(String error, String message, String traceId) {
        super(error, message);
        this.traceId = traceId;
    }
}
