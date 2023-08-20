package com.example.usermanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {

    @NotBlank
    private String account;

    @NotBlank
    private String password;
}
