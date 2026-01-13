package com.lollo.menuplanner.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service to get logged-in users and its id provided by google for uniq identification
 * Is uses when called for in method.
 * */

@Service
public class LoggedInUser {

    public String getProviderId(){
        return Optional.of(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .map(principal -> {
                return switch (principal) {
                    case OAuth2User oAuth2User -> (String) oAuth2User.getAttribute("sub");
                    case UserDetails userDetails -> userDetails.getUsername();
                    case String username -> username;
                    default -> throw new IllegalArgumentException("Invalid user");
                };
            })
            .orElseThrow(() -> new IllegalStateException("No logged in user"));
    }
}
