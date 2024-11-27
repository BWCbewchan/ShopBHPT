package com.shop.bhpt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shop.bhpt.dto.AuthenticationRequest;
import com.shop.bhpt.dto.AuthenticationResponse;
import com.shop.bhpt.dto.RegisterRequest;
import com.shop.bhpt.entities.Role;
import com.shop.bhpt.entities.User;
import com.shop.bhpt.exceptions.CustomException;
import com.shop.bhpt.repositories.UserRepository;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest request) {
        // Kiểm tra username đã tồn tại
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException("Username đã tồn tại!");
        }

        // Tạo user mới
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setRole(Role.USER);

        userRepository.save(user);

        return new AuthenticationResponse("Đăng ký thành công!");
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException("User không tồn tại!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException("Sai mật khẩu!");
        }

        return new AuthenticationResponse("Đăng nhập thành công!");
    }
} 