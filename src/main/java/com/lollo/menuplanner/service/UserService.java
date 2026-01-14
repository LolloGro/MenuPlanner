package com.lollo.menuplanner.service;

import com.lollo.menuplanner.entity.Role;
import com.lollo.menuplanner.entity.User;
import com.lollo.menuplanner.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /**
     * Creates a new user and saves in database
     * The method handles if another request creates the same user concurrently,
     * then a new attempt to find user by providerId is attempted
     * */
    private User createUser(String name, String email, String provider, String providerId){

       try{
           User newUser = new User();

           newUser.setName(name);
           newUser.setEmail(email);
           newUser.setRole(Role.USER);
           newUser.setProvider(provider);
           newUser.setProviderId(providerId);
           return userRepository.save(newUser);
       }catch (DataIntegrityViolationException ex){
           return userRepository.findUserByProviderId(providerId).orElseThrow(() ->
            new IllegalStateException("User with "+providerId+" was created concurrently but could not be found", ex)
               );
       }

    }

    @Transactional
    public void findOrCreateUser(String name, String email, String provider, String providerId) {
        userRepository.findUserByProviderId(providerId).orElseGet(() ->
            createUser(name, email, provider, providerId));
    }

    //public deleteUser(){}
}
