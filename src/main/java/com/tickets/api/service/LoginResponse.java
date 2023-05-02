package com.tickets.api.service;

import com.tickets.api.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginResponse {
	private String accessToken;
	private long expiresIn;
	private String tokenType;
	private String tenant;
	private String email;
	private String firstName;
	private String lastName;
	private List<Role> roles;
}
