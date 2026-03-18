package com.javalord.auth_service.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.javalord.auth_service.client.UserServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserServiceClient userServiceClient;
    private final ObjectMapper objectMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Loading UserDetails for user {}", email);

        Map<String, Object> response = userServiceClient.getUserAuthByEmail(email);
        JsonNode jsonNode = objectMapper.valueToTree(response);

        log.info("Loading UserDetails for user {}", jsonNode);

        if (!jsonNode.get("status").asText().equals("SUCCESS")) {
            throw new UsernameNotFoundException(email);
        }

        String username = jsonNode.get("data").get("email").asText();
        String password = jsonNode.get("data").get("password").asText();
        List<GrantedAuthority>  authorities = new ArrayList<>();

        ArrayNode rolesNode = (ArrayNode) jsonNode.get("data").get("roles");

        rolesNode.valueStream().forEach(role -> {
           authorities.add(new SimpleGrantedAuthority(role.asText()));
        });

        return new User(
                username,
                password,
                authorities
        );
    }
}
