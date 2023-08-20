package com.example.usermanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class JwtAuthenticationTokenDTO {
    private String jwtToken;
    private String userId;
    private String name;
    private List<String> roleNameList;
}
