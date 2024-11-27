package com.shop.bhpt.services;

import com.shop.bhpt.dto.AuthResponse;
import com.shop.bhpt.dto.AuthResponseForServiceUser;
import com.shop.bhpt.dto.ChangePasswordRequest;
import com.shop.bhpt.dto.LoginRequest;
import com.shop.bhpt.dto.RegisterRequest;
import com.shop.bhpt.dto.UpdateProfileRequest;
import com.shop.bhpt.dto.UserResponse;
import com.shop.bhpt.entities.Role;
import com.shop.bhpt.entities.User;
import com.shop.bhpt.exceptions.DuplicateResourceException;
import com.shop.bhpt.exceptions.InvalidPasswordException;
import com.shop.bhpt.exceptions.ResourceNotFoundException;
import com.shop.bhpt.exceptions.UnauthorizedException;
import com.shop.bhpt.repositories.UserRepository;
import com.shop.bhpt.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private  UserRepository userRepository;
    private  PasswordEncoder passwordEncoder;
    private  JwtService jwtService;
    private  AuthenticationManager authenticationManager;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User not authenticated");
        }

        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User register(RegisterRequest request) {
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setRole(Role.USER);
        

        return userRepository.save(user);
    }

    public AuthResponseForServiceUser login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = getCurrentUser();
        String token = jwtService.generateToken(user);

        AuthResponseForServiceUser responseForServiceUser = new AuthResponseForServiceUser();
        responseForServiceUser.setToken(token);
        responseForServiceUser.setToken("Bearer");
        responseForServiceUser.setUser(UserResponse.fromUser(user));
        return responseForServiceUser;
    }

    public User updateProfile(UpdateProfileRequest request) {
        User user = getCurrentUser();
        
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateResourceException("Email already exists");
            }
            user.setEmail(request.getEmail());
        }

        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }

        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }

        if (request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }

        return userRepository.save(user);
    }

    public void changePassword(ChangePasswordRequest request) {
        User user = getCurrentUser();

        // Verify old password
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid old password");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
} 