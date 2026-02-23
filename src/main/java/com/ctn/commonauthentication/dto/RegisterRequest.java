package com.ctn.commonauthentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    private String password;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Postal code is required")
    private String postalCode;

    @NotBlank(message = "Prefecture is required")
    private String prefecture;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Role is required")
    private String role;
}
