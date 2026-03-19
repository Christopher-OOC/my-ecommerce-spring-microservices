package org.javalord.apigateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil(@Value("${app.security.jwt.secret-key}") String secret) {
        byte[] decoded = Base64.getDecoder().decode(secret.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(decoded);
    }

    public boolean isTokenValid(String token) {
        try {
            return extractClaims(token).getExpiration().before(new Date());
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
