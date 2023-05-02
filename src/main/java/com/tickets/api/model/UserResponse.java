package com.tickets.api.model;

import com.tickets.api.entity.UserEntity;
import com.tickets.api.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;
import java.util.Set;

@Builder
@Data
public class UserResponse {
	private String email;
	private String id;
	private String firstName;
	private String lastName;
	private Set<Role> roles;

	public static UserResponse fromEntity(UserEntity userEntity) {
		return Optional.ofNullable(
				userEntity
		).map(
				tenant -> UserResponse.builder()
						.email(tenant.getEmail())
						.id(tenant.getId().toString())
						.firstName(tenant.getFirstName())
						.lastName(tenant.getLastName())
						.roles(tenant.getRoles())
						.build()
		).orElse(null);
	}

}
