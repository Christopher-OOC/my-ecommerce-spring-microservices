package com.javalord.user_service.user;

import com.javalord.user_service.user.dto.UserCreateRequest;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User createRequestToUser(UserCreateRequest request) {
        return User
                .builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }
}
