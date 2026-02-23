package com.ctn.commonauthentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private boolean success;

    @JsonProperty("already_existed")
    private boolean alreadyExisted;

    private String message;
}
