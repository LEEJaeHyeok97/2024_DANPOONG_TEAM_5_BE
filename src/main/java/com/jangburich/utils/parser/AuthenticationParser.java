package com.jangburich.utils.parser;

import com.jangburich.domain.oauth.domain.CustomOAuthUser;
import org.springframework.security.core.Authentication;

public class AuthenticationParser {
    private AuthenticationParser(){}

    public static Long parseUserId(Authentication authentication) {
        CustomOAuthUser principal = (CustomOAuthUser) authentication.getPrincipal();
        return Long.parseLong(principal.getUserId());
    }
}
