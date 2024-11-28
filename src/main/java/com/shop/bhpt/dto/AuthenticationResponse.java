package com.shop.bhpt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
	private String message;
	private String token;
	
	public AuthenticationResponse(String message) {
		super();
		this.message = message;
	}
}