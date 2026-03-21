package com.javalord.user_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
public class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil(@Value("${app.security.jwt.secret-key}") String secret) {
        byte[] decoded = Base64.getDecoder().decode(secret.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(decoded);
    }

    public boolean isTokenValid(String token) {
        try {
            log.info("Created At: {}", extractClaims(token).getIssuedAt().toString());
            log.info("Expiration At: {}", extractClaims(token).getExpiration().toString());

            return extractClaims(token).getExpiration().after(new Date());
        }
        catch (JwtException ex) {
            throw new JwtException(ex.getMessage());
        }
    }

    public Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(this.secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
        catch (JwtException e) {
            throw new JwtException("Invalid token");
        }
    }

}
