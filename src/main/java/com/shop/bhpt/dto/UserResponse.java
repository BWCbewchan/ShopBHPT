package com.shop.bhpt.dto;

import com.shop.bhpt.entities.Role;
import com.shop.bhpt.entities.User;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private Role role;

    // Constructor đầy đủ tham số
    public UserResponse(Long id, String username, String email, String fullName, 
                       String phone, String address, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    // Constructor mặc định
    public UserResponse() {
    }

    public static UserResponse fromUser(User user) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFullName(),
            user.getPhone(),
            user.getAddress(),
            user.getRole()
        );
    }

    // Hoặc sử dụng setter
    /*
    public static UserResponse fromUser(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setPhone(user.getPhone());
        response.setAddress(user.getAddress());
        response.setRole(user.getRole());
        return response;
    }
    */
}