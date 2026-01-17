package com.lollo.menuplanner.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomOAuth2Service customOAuth2Service;

    public SecurityConfig(final CustomOAuth2Service customOAuth2Service) {
        this.customOAuth2Service = customOAuth2Service;
    }

    //Hantera endpoints för meal och menu
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index.html", "/assets/**").permitAll()
                .requestMatchers("/oauth2/**").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll())
            .oauth2Login(oauth -> oauth
                .userInfoEndpoint(user -> user
                    .userService(customOAuth2Service))
                .defaultSuccessUrl("/", true))
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID"))
            .csrf(AbstractHttpConfigurer::disable)
            .build();
    }
}
