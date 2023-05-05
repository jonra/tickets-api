package com.tickets.api.model;

import com.tickets.api.entity.EventEntity;
import com.tickets.api.entity.TicketEntity;
import com.tickets.api.enums.ConcessionType;
import com.tickets.api.enums.TicketType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {
	@NotNull
	private String name;
	private TicketType ticketType;
	private ConcessionType concessionType;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Integer numberOfTickets;
	private BigDecimal price;

	public static List<EventEntity> toEntity(List<TicketRequest> eventList, String tenantId) {
		return Optional.ofNullable(eventList)
				.map(e -> toEntity(e, tenantId))
				.orElse(null);

	}

	public static TicketEntity toEntity(TicketRequest ticket, String tenantId) {
		return Optional.ofNullable(ticket).map(e -> TicketEntity.builder()
						.name(ticket.getName())
						.ticketType(ticket.getTicketType())
						.concessionType(ticket.getConcessionType())
						.startTime(ticket.getStartTime())
						.endTime(ticket.getEndTime())
						.numberOfTickets(ticket.getNumberOfTickets())
						.price(ticket.getPrice())
						.tenantId(tenantId)
						.build())
				.orElse(null);
	}

}
