package com.shop.bhpt.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    @Email(message = "Invalid email format")
    private String email;
    private String fullName;
    private String phone;
    private String address;
    
    
    
	public UpdateProfileRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UpdateProfileRequest(@Email(message = "Invalid email format") String email, String fullName, String phone,
			String address) {
		super();
		this.email = email;
		this.fullName = fullName;
		this.phone = phone;
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
    
    
} 