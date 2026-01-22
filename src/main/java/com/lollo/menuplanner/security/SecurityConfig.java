package com.lollo.menuplanner.security;

import jakarta.servlet.http.HttpServletResponse;
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/index.html", "/assets/**", "/api/auth").permitAll()
                .requestMatchers("/oauth2/**").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll())
            .oauth2Login(oauth -> oauth
                .userInfoEndpoint(user -> user
                    .userService(customOAuth2Service))
                .defaultSuccessUrl("/", true))
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((req, resp,e) -> {
                    if(req.getRequestURI().startsWith("/api")){
                        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    }else{
                        resp.sendRedirect("/login");
                    }
                }))
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID"))
            .csrf(AbstractHttpConfigurer::disable)
            .build();
    }
}
