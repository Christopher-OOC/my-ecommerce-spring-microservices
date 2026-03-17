package com.javalord.user_service.user;

import com.javalord.user_service.user.dto.UserCreateRequest;
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
}
