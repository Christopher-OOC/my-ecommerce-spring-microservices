package com.javalord.user_service.user.dto;

import com.javalord.user_service.role.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserAuthResponse {

    private String email;
    private String password;
    private Set<String> roles;

}
