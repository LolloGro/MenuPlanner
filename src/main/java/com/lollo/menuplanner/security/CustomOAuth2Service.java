package com.lollo.menuplanner.security;

import com.lollo.menuplanner.service.UserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2Service extends DefaultOAuth2UserService {

    private final UserService userService;

    public CustomOAuth2Service(UserService userService) {
        this.userService = userService;
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest user) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(user);

        String  name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");
        String provider = user.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");

        if(providerId == null){
            throw new OAuth2AuthenticationException("Provider id not found");
        }

        userService.findOrCreateUser(name, email, provider, providerId);

        return oAuth2User;
    }

}
