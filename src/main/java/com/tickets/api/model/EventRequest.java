package com.tickets.api.model;

import com.tickets.api.entity.EventEntity;
import com.tickets.api.enums.EventType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
	@NotNull
	private String name;
	private EventType type;

	public static List<EventEntity> toEntity(List<EventRequest> eventList, String tenantId) {
		return Optional.ofNullable(eventList)
				.map(e -> toEntity(e, tenantId))
				.orElse(null);

	}

	public static EventEntity toEntity(EventRequest event, String tenantId) {
		return Optional.ofNullable(event).map(e -> EventEntity.builder()
						.name(event.getName())
						.type(event.getType())
						.tenantId(tenantId)
						.build())
				.orElse(null);
	}

}
