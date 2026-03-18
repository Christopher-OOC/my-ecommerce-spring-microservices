package com.javalord.auth_service.security;

import com.javalord.auth_service.dto.AuthResponse;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Service
public class JwtService {

    @Value("${app.security.jwt.access-token-expiration}")
    private Long accessTokenExpiration;

    @Value("${app.security.jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    private final SecretKey secretKey;

    public JwtService(@Value("${app.security.jwt.secret-key}") String secret) {
        byte[] decoded = Base64.getDecoder().decode(secret.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(decoded);
    }


    public AuthResponse generateLoginToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Invalid username/password supplied");
        }
        String username = (String)authentication.getPrincipal();

        return buildToken(username, Map.of("TOKEN_TYPE", "ACCESS_TOKEN"), accessTokenExpiration);
    }

    public AuthResponse refreshAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Invalid username/password supplied");
        }
        String username = (String)authentication.getPrincipal();

        return buildToken(username, Map.of("TOKEN_TYPE", "REFRESH_TOKEN"), refreshTokenExpiration);
    }
}
