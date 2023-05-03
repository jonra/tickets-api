package com.tickets.api.model;

import com.tickets.api.entity.TicketEntity;
import com.tickets.api.enums.TicketType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TicketResponse {
	String id;
	String name;
	TicketType type;
	List<ExtraResponse> extras;

	public static TicketResponse fromEntity(TicketEntity entity) {
		return TicketResponse.builder()
				.id(String.valueOf(entity.getId()))
				.name(entity.getName())
				.type(entity.getType())
				.extras(ExtraResponse.fromEntity(entity.getExtras()))
				.build();
	}
}
