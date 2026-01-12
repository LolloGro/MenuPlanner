package com.lollo.menuplanner.security;

import com.lollo.menuplanner.entity.User;
import com.lollo.menuplanner.service.UserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomOath2Service extends DefaultOAuth2UserService {

    private final UserService userService;

    public CustomOath2Service(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest user) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(user);

        String  name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");
        String provider = user.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");

        User checkForUser = userService.findUserByProviderId(providerId);

        if(checkForUser == null){
        userService.CreateUser(name, email, provider, providerId);
        }

        return oAuth2User;
    }

}
