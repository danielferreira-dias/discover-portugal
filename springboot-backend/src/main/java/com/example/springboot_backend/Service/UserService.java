package com.example.springboot_backend.Service;

import com.example.springboot_backend.Entity.User;
import com.example.springboot_backend.exception.EmailAlreadyExistsException;
import com.example.springboot_backend.exception.UserNotFoundException;
import com.example.springboot_backend.repository.UserRepository;
import com.example.springboot_backend.Request.UserRequest;
import com.example.springboot_backend.Response.UserResponse;
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

    public UserResponse updateUser(final Long userId) {
        final User user = this.userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found"));

        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    public UserResponse deleteUser(final Long userId) {
        final User user = this.userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found"));

        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();

    }

    public UserResponse getUserById(final Long userId) {
        final User user = this.userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found"));

        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

}
