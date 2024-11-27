package com.shop.bhpt.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseForServiceUser {
	private String token;
    private String type;
    private UserResponse user;
    
    
	public AuthResponseForServiceUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AuthResponseForServiceUser(String token, String type, UserResponse user) {
		super();
		this.token = token;
		this.type = type;
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public UserResponse getUser() {
		return user;
	}
	public void setUser(UserResponse user) {
		this.user = user;
	}
    
    
}
