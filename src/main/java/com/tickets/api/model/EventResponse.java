package com.tickets.api.model;

import com.tickets.api.entity.EventEntity;
import com.tickets.api.enums.EventType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventResponse {
	String id;
	String name;
	EventType type;
	public static EventResponse fromEntity(EventEntity entity) {
		return EventResponse.builder()
				.id(String.valueOf(entity.getId()))
				.name(entity.getName())
				.type(entity.getType())
				.build();
	}
}
