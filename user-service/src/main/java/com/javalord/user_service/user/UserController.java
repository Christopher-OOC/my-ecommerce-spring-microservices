package com.javalord.user_service.user;

import com.javalord.user_service.common.RestResponse;
import com.javalord.user_service.common.Status;
import com.javalord.user_service.user.dto.UserAuthResponse;
import com.javalord.user_service.user.dto.UserCreateRequest;
import com.javalord.user_service.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RestResponse<String> addUser(@Valid @RequestBody UserCreateRequest request) {
        userService.createUser(request);

        RestResponse<String> response = new RestResponse<>(
                Status.SUCCESS,
                "User created successfully",
                "User created successfully"
        );

        return response;
    }

    @GetMapping(value = "/{userId}")
    public RestResponse<UserResponse>  getUserById(@PathVariable long userId) {
        UserResponse user = userService.findUserById(userId);

        RestResponse<UserResponse> response = new RestResponse<>(
                Status.SUCCESS,
                "User retrieved successfully",
                user
        );

        return response;
    }

    @GetMapping(value = "/getByEmail/{email}")
    public RestResponse<UserResponse>  getUserByEmail(@PathVariable String email) {
        UserResponse user = userService.getUserByEmail(email);

        RestResponse<UserResponse> response = new RestResponse<>(
                Status.SUCCESS,
                "User retrieved successfully",
                user
        );

        return response;
    }

    @GetMapping(value = "/{email}/internal/auth")
    public RestResponse<UserAuthResponse>  getUserAuth(
            @PathVariable String email,
            @RequestHeader(name = "Internal-Api-Key") String apiKey
    ) {
        UserAuthResponse user = userService.getUserAuthByEmail(email, apiKey);

        RestResponse<UserAuthResponse> response = new RestResponse<>(
                Status.SUCCESS,
                "User auth retrieved successfully",
                user
        );

        return response;
    }
}
