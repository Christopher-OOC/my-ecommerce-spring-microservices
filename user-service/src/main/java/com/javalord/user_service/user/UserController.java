package com.javalord.user_service.user;

import com.javalord.user_service.common.RestResponse;
import com.javalord.user_service.common.Status;
import com.javalord.user_service.user.dto.UserCreateRequest;
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
}
