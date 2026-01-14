package com.lollo.menuplanner.security;

import com.lollo.menuplanner.service.LoggedInUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
/**
 * Configuration that is automatically used by security and setting @CreatedBy
 * */

@Configuration
@Profile("!test")
public class SpringSecurityAuditorAware {

    private final LoggedInUser loggedInUser;

    public SpringSecurityAuditorAware(LoggedInUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @Bean(name = "auditorProvider")
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(loggedInUser.getProviderId());
    }

}
