package com.tickets.api.model;

import com.tickets.api.entity.TicketEntity;
import com.tickets.api.enums.ConcessionType;
import com.tickets.api.enums.TicketType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TicketResponse {
	private String id;
	private String name;
	private TicketType ticketType;
	private ConcessionType concessionType;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Integer numberOfTickets;
	private BigDecimal price;
	List<ExtraResponse> extras;

	public static TicketResponse fromEntity(TicketEntity entity) {
		return TicketResponse.builder()
				.id(String.valueOf(entity.getId()))
				.name(entity.getName())
				.ticketType(entity.getTicketType())
				.concessionType(entity.getConcessionType())
				.startTime(entity.getStartTime())
				.endTime(entity.getEndTime())
				.numberOfTickets(entity.getNumberOfTickets())
				.price(entity.getPrice())
				.extras(ExtraResponse.fromEntity(entity.getExtras()))
				.build();
	}
}
