package com.tickets.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserProfile {
	private String firstName;
	private String lastName;
	private String email;
	private String tenantId;
	private List<String> roles;
	private String userId;
	private String organiserId;
}
