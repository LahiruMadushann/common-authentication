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
public class AreaDetails {

    @JsonProperty("zip_code")
    private String zipCode;

    @JsonProperty("prefecture")
    private String prefecture;

    @JsonProperty("municipality")
    private String municipality;
}
