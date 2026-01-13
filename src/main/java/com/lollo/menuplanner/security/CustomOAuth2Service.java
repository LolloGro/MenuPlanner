package com.lollo.menuplanner.security;

import com.lollo.menuplanner.service.UserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomOAuth2Service extends DefaultOAuth2UserService {

    private final UserService userService;

    public CustomOAuth2Service(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest user) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(user);

        String  name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");
        String provider = user.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");

        if(providerId == null){
            throw new OAuth2AuthenticationException("Provider not found");
        }

        try{
            userService.findUserByProviderId(providerId)
                .orElseGet(() -> userService.createUser(name, email, provider, providerId));
        } catch (Exception e) {
            throw new OAuth2AuthenticationException("Could not create user");
        }

        return oAuth2User;
    }

}
