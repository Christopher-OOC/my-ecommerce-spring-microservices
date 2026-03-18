package com.javalord.user_service.user;

import com.javalord.user_service.user.dto.UserAuthResponse;
import com.javalord.user_service.user.dto.UserCreateRequest;
import com.javalord.user_service.user.dto.UserResponse;
import org.springframework.stereotype.Service;

import java.util.Set;

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

    public UserResponse mapUserToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        return userResponse;
    }

    public UserAuthResponse mapUserToAuthResponse(User user) {
        UserAuthResponse userAuthResponse = new UserAuthResponse();
        userAuthResponse.setEmail(user.getEmail());
        userAuthResponse.setPassword(user.getPassword());
//        userAuthResponse.setRoles(user.getRoles());

        userAuthResponse.setRoles(Set.of("ROLE_USER", "ROLE_ADMIN"));

        return userAuthResponse;
    }
}
