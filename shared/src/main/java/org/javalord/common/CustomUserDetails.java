package org.javalord.common;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Long userId;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private String token;

    public CustomUserDetails(Long userId, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public CustomUserDetails(Long userId, String email, String password, Collection<? extends GrantedAuthority> authorities, String token) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
