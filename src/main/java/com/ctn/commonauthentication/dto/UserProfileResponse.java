package com.ctn.commonauthentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

    private String id;

    private String email;

    private String role;

    private String status;

    private String name;

    private String phone;

    @JsonProperty("postal_code")
    private String postalCode;

    private String prefecture;

    private String city;
}
