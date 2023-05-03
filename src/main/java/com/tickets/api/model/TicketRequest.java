package com.tickets.api.model;

import com.tickets.api.entity.EventEntity;
import com.tickets.api.entity.TicketEntity;
import com.tickets.api.enums.TicketType;
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
public class TicketRequest {
	@NotNull
	private String name;
	private TicketType type;

	public static List<EventEntity> toEntity(List<TicketRequest> eventList, String tenantId) {
		return Optional.ofNullable(eventList)
				.map(e -> toEntity(e, tenantId))
				.orElse(null);

	}

	public static TicketEntity toEntity(TicketRequest ticket, String tenantId) {
		return Optional.ofNullable(ticket).map(e -> TicketEntity.builder()
						.name(ticket.getName())
						.type(ticket.getType())
						.tenantId(tenantId)
						.build())
				.orElse(null);
	}

}
