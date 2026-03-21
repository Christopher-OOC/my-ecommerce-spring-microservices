package com.javalord.auth_service.auth;

import com.javalord.auth_service.dto.AuthResponse;
import com.javalord.auth_service.dto.LoginRequest;

import com.javalord.auth_service.dto.RefreshTokenRequest;
import com.javalord.auth_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        return jwtService.generateLoginToken(authentication);
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        return jwtService.refreshAccessToken(request.getRefreshToken());
    }
}
