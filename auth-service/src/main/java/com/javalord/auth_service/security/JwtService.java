package com.javalord.auth_service.security;

import com.javalord.auth_service.dto.AuthResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.javalord.common.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

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

    public AuthResponse generateLoginToken(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Invalid username/password supplied");
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        long userId = userDetails.getUserId();
        String username = userDetails.getUsername();
        String authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        Map<String, Object> claims = Map.of(
                "TOKEN_TYPE", "ACCESS_TOKEN",
                "roles", authorities,
                "userId", userId
        );

        String accessToken = generateAccessToken(username, claims, accessTokenExpiration);
        String refreshToken = generateRefreshToken(username, refreshTokenExpiration);

        return new AuthResponse(
                accessToken,
                refreshToken,
                "Bearer"
        );
    }

    public AuthResponse refreshAccessToken(String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Invalid username/password supplied");
        }

        if (!checkIfRefreshTokenValid(token)) {
            throw new JwtException("Invalid refresh token");
        }

        String username = (String)authentication.getPrincipal();

        String accessToken = generateAccessToken(username, Map.of("TOKEN_TYPE", "ACCESS_TOKEN"), accessTokenExpiration);
        String refreshToken = generateRefreshToken(username, refreshTokenExpiration);

        return new AuthResponse(
                accessToken,
                refreshToken,
                "Bearer"
        );
    }

    private boolean checkIfRefreshTokenValid(String token) {
        String tokenType = (String)extractClaims(token).get("TOKEN_TYPE");
        return tokenType.equals("REFRESH_TOKEN");
    }

    private Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(this.secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
        catch (JwtException e) {
            throw new JwtException("Invalid refresh token");
        }
    }

    private String generateAccessToken(String email, Map<String, Object> claims, long expiration) {
        return buildToken(email, claims,  expiration);
    }

    private String generateRefreshToken(String email, long expiration) {
        return buildToken(email, Map.of("TOKEN_TYPE", "REFRESH_TOKEN"), expiration);
    }

    private String buildToken(String email, Map<String, Object> claims, Long expiration) {
        try {
            return Jwts.builder()
                    .signWith(this.secretKey)
                    .claims(claims)
                    .subject(email)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 24 * 50 * expiration))
                    .compact();
        }
        catch (JwtException e) {
            throw new JwtException("Invalid while creating token!");
        }
    }
}
