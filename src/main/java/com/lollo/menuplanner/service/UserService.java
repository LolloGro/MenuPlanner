package com.lollo.menuplanner.service;

import com.lollo.menuplanner.entity.Role;
import com.lollo.menuplanner.entity.User;
import com.lollo.menuplanner.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void CreateUser(String name, String email, String provider, String providerId){
        User newUser = new User();

        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setRole(Role.USER);
        newUser.setProvider(provider);
        newUser.setProviderId(providerId);
        userRepository.save(newUser);
    }

    public User findUserByProviderId(String providerId){
        return userRepository.findUserByProviderId(providerId).orElse(null);
    }

    //public deleteUser(){}
}
