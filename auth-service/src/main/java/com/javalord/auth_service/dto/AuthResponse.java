package com.javalord.auth_service.dto;

import lombok.Data;

@Data
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType;

}
