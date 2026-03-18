package com.javalord.auth_service.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.Map;

public interface UserServiceClient {

    @GetExchange(value = "/api/v1/users/{userId}")
    public Map<String, Object> getUseById(@PathVariable String userId);

    @GetExchange(value = "/api/v1/users/getByEmail/{email}")
    public Map<String, Object> getUserByEmail(@PathVariable String email);

    @GetExchange(value = "/api/v1/users/{email}/internal/auth")
    public Map<String, Object> getUserAuthByEmail(@PathVariable String email);
}
