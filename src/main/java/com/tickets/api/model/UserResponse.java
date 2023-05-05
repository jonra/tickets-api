package com.tickets.api.model;

import com.tickets.api.entity.UserEntity;
import com.tickets.api.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
public class UserResponse {
	private String email;
	private String id;
	private String firstName;
	private String lastName;
	private Set<Role> roles;
	private OrganiserResponse organiser;

	public static List<UserResponse> fromEntity(List<UserEntity> userEntities) {
		if (userEntities == null) {
			return null;
		}

		return userEntities.stream().map(UserResponse::fromEntity).collect(Collectors.toList());
	}
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
						.organiser(OrganiserResponse.fromEntity(tenant.getOrganiser()))
						.build()
		).orElse(null);
	}

}
