package com.tickets.api.model;

import com.tickets.api.entity.OrganiserEntity;
import com.tickets.api.enums.OrganiserType;
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
public class OrganiserRequest {
	@NotNull
	private String name;
	private OrganiserType type;

	public static List<OrganiserEntity> toEntity(List<OrganiserRequest> eventList, String tenantId) {
		return Optional.ofNullable(eventList)
				.map(e -> toEntity(e, tenantId))
				.orElse(null);

	}

	public static OrganiserEntity toEntity(OrganiserRequest event, String tenantId) {
		return Optional.ofNullable(event).map(e -> OrganiserEntity.builder()
						.name(event.getName())
						.type(event.getType())
						.tenantId(tenantId)
						.build())
				.orElse(null);
	}

}
