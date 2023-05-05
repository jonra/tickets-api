package com.tickets.api.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickets.api.entity.OrganiserEntity;
import com.tickets.api.enums.OrganiserType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganiserResponse {
	String id;
	String name;
	OrganiserType type;
	public static OrganiserResponse fromEntity(OrganiserEntity entity) {
		if (entity == null) {
			return null;
		}
//		List<String> users = null;
//		if (entity.getUsers() != null) {
//			 users = entity.getUsers().stream().map(user -> user.getId().toString()).collect(Collectors.toList());
//		}
		return OrganiserResponse.builder()
				.id(String.valueOf(entity.getId()))
				.name(entity.getName())
				.type(entity.getType())
				.build();
	}

	public String toJson() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
}
