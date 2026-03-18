package com.javalord.user_service.user;

import com.javalord.user_service.common.ErrorMessages;
import com.javalord.user_service.exception.BusinessException;
import com.javalord.user_service.user.dto.UserAuthResponse;
import com.javalord.user_service.user.dto.UserCreateRequest;
import com.javalord.user_service.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.internal.key}")
    private  String internalKey;

    public void createUser(UserCreateRequest request) {
        User user = userMapper.createRequestToUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public UserResponse findUserById(long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorMessages.USER_NOT_FOUND));

        return userMapper.mapUserToResponse(user);
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorMessages.USER_NOT_FOUND));

        return userMapper.mapUserToResponse(user);
    }

    public UserAuthResponse getUserAuthByEmail(String email, String apiKey) {

        if (!internalKey.equals(apiKey)) {
            throw new BusinessException(ErrorMessages.INTERNAL_KEY_NOT_VALID);
        }

        log.info("Communicating internal with key {}", apiKey);

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorMessages.USER_NOT_FOUND));

        return userMapper.mapUserToAuthResponse(user);
    }
}
