package com.javalord.user_service.user.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserAuthResponse {

    private long userId;
    private String email;
    private String password;
    private Set<String> roles;

}
