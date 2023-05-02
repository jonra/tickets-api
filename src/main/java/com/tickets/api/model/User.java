package com.tickets.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String tenant;

}
