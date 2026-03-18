package com.javalord.user_service.user;

import com.javalord.user_service.common.ErrorMessages;
import com.javalord.user_service.exception.BusinessException;
import com.javalord.user_service.user.dto.UserCreateRequest;
import com.javalord.user_service.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void createUser(UserCreateRequest request) {
        User user = userMapper.createRequestToUser(request);

        // todo // encrypt password

        userRepository.save(user);
    }

    public UserResponse findUserById(long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorMessages.USER_NOT_FOUND));

        return userMapper.mapUserToResponse(user);
    }
}
