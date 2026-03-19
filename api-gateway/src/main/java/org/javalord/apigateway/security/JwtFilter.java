package org.javalord.apigateway.security;

import io.jsonwebtoken.JwtException;import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = authHeader.substring(7);

        try {
            if (!this.jwtUtil.isTokenValid(jwtToken)) {
                throw new JwtException("Invalid JWT Token");
            }

            String email = this.jwtUtil.extractClaims(jwtToken).getSubject();
            if (email != null) {
                var authentication = new UsernamePasswordAuthenticationToken(email, null);

                var details = new WebAuthenticationDetailsSource().buildDetails(request);
                authentication.setDetails(details);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        }
        catch (JwtException e) {
            throw new JwtException("Invalid JWT Token");
        }
    }
}
