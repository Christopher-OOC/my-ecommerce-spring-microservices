package com.javalord.auth_service.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.Map;

public interface UserServiceClient {

    @GetExchange(value = "/{userId}")
    public Map<String, Object> getUseById(@PathVariable String userId);

    @GetExchange(value = "/getByEmail/{email}")
    public Map<String, Object> getUserByEmail(@PathVariable String email);
}
