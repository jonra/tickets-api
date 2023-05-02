package com.tickets.api.model;

import com.tickets.api.entity.OrganiserEntity;
import com.tickets.api.enums.OrganiserType;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class OrganiserResponse {
	String id;
	String name;
	OrganiserType type;
	List<String> users;
	public static OrganiserResponse fromEntity(OrganiserEntity entity) {
		List<String> users = null;
		if (entity.getUsers() != null) {
			 users = entity.getUsers().stream().map(user -> user.getId().toString()).collect(Collectors.toList());
		}
		return OrganiserResponse.builder()
				.id(String.valueOf(entity.getId()))
				.name(entity.getName())
				.type(entity.getType())
				.users(users)
				.build();
	}
}
