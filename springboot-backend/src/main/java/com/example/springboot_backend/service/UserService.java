package com.example.springboot_backend.service;

import com.example.springboot_backend.entity.User;
import com.example.springboot_backend.exceptions.EmailAlreadyExistsException;
import com.example.springboot_backend.repository.UserRepository;
import com.example.springboot_backend.request.UserRequest;
import com.example.springboot_backend.response.UserResponse;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest request){
        // Bussiness Logic
        System.out.println("User Business Logic");

        // Check if User email already exists
        if (this.userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        /*
        // Getting Data
        final User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encoder(request.getPassword()))
                .build();

        // Saving
        userRepository.save(user);
         */

        return UserResponse.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
    }

}
