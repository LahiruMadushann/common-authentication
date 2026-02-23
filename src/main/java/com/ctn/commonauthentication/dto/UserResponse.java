package com.ctn.commonauthentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private boolean success;
    private UserData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserData {
        private String name;
        private String email;

        @JsonProperty("phone_number")
        private String phoneNumber;

        @JsonProperty("postal_code")
        private String postalCode;

        private String prefecture;
        private String city;
        private String role;

        @JsonProperty("created_at")
        private LocalDateTime createdAt;
    }
}
