package com.tickets.api.model;

import com.tickets.api.entity.UserEntity;
import com.tickets.api.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Builder
@Data
public class UserRequest {
	@NotNull private String email;
	@NotNull private String password;
	private String firstName;
	private String lastName;
	private Set<Role> roles;

	public static List<UserEntity> toEntity(List<UserRequest> eventList, String tenantId) {
		return Optional.ofNullable(eventList)
				.map(e -> toEntity(e, tenantId))
				.orElse(null);

	}

	public static UserEntity toEntity(UserRequest user, String tenantId) {
		return Optional.ofNullable(user).map(e -> UserEntity.builder()
						.email(user.getEmail())
						.password(user.getPassword())
						.firstName(user.getFirstName())
						.lastName(user.getLastName())
						.roles(user.getRoles())
						.tenantId(tenantId)
						.build())
				.orElse(null);
	}
}
