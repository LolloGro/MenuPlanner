package com.lollo.menuplanner.service;

import com.lollo.menuplanner.entity.Role;
import com.lollo.menuplanner.entity.User;
import com.lollo.menuplanner.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String name, String email, String provider, String providerId){
        User newUser = new User();

        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setRole(Role.USER);
        newUser.setProvider(provider);
        newUser.setProviderId(providerId);
         return userRepository.save(newUser);
    }

    public Optional<User> findUserByProviderId(String providerId){
        return userRepository.findUserByProviderId(providerId);
    }

    //public deleteUser(){}
}
