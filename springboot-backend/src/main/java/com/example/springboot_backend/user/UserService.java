package com.example.springboot_backend.user;

import com.example.springboot_backend.exception.EmailAlreadyExistsException;
import com.example.springboot_backend.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponse> getAllUsers(){
        final List<User> users = this.userRepository.findAll();
        return users.stream().map(user -> UserResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build()).collect(Collectors.toList());
    }

    public UserResponse createUser(UserRequest request){
        // Check if User email already exists
        if (this.userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("This email already exists");
        }

        final User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return UserResponse.builder()
                .userId(user.getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
    }

    public UserResponse updateUser(final Long userId, UserRequest request) {
        final User user = this.userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);

        return UserResponse.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    public UserResponse deleteUser(final Long userId) {
        final User user = this.userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found"));

        userRepository.delete(user);

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
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}

