package org.javalord.cartservice.client;

import org.javalord.common.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface UserRestClientService {

    @GetExchange(value = "/api/v1/users/{userId}")
    UserResponse getUser(@PathVariable Long userId);

}
