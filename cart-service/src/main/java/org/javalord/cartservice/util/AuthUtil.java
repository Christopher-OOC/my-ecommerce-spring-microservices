package org.javalord.cartservice.util;

import org.javalord.common.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    private static CustomUserDetails getPrincipal() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new RuntimeException("No authenticated user");
        }

        return (CustomUserDetails) auth.getPrincipal();
    }

    public static Long getCurrentUserId() {
        return getPrincipal().getUserId();
    }

    public static String getToken() {
        return getPrincipal().getToken();
    }

}