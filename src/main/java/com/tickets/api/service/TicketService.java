package com.tickets.api.service;

import com.tickets.api.entity.EventEntity;
import com.tickets.api.entity.ExtraEntity;
import com.tickets.api.entity.TicketEntity;
import com.tickets.api.exceptions.EntityNotFoundException;
import com.tickets.api.model.TicketRequest;
import com.tickets.api.model.TicketResponse;
import com.tickets.api.repository.EventRepository;
import com.tickets.api.repository.ExtraRepository;
import com.tickets.api.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {
	private final TicketRepository ticketRepository;
	private final EventRepository eventRepository;
	private final ExtraRepository extraRepository;

	public TicketResponse createTicket(TicketRequest deliveryPolygonRequest, String organiserId, String eventId, String tenantId) {
		EventEntity event = eventRepository.findByIdAndTenantIdAndOrganiserId(UUID.fromString(eventId), tenantId, UUID.fromString(organiserId))
				.orElseThrow(() -> new EntityNotFoundException("Event not found"));

		TicketEntity ticketEntity = TicketRequest
				.toEntity(deliveryPolygonRequest, tenantId);
		ticketEntity.setEvent(event);
		TicketEntity save = ticketRepository.save(ticketEntity);

		event.getTickets().add(save);
		return TicketResponse.fromEntity(save);
	}

	public TicketResponse getTicket(String ticketId, String tenant) {
		TicketEntity ticket = ticketRepository.findByIdAndTenantId(UUID.fromString(ticketId), tenant)
				.orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
		return TicketResponse.fromEntity(ticket);
	}

	public TicketResponse addExtraToTicket(String tenantId, String organiserId, String eventId, String ticketId, String extraId) {
		EventEntity event = eventRepository.findByIdAndTenantIdAndOrganiserId(UUID.fromString(eventId), tenantId, UUID.fromString(organiserId))
				.orElseThrow(() -> new EntityNotFoundException("Event not found"));

		TicketEntity ticket = ticketRepository.findByIdAndEventIdAndTenantId((UUID.fromString(ticketId)), UUID.fromString(eventId), tenantId)
				.orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

		ExtraEntity extra = extraRepository.findByIdAndTenantIdAndOrganiserId(UUID.fromString(extraId), tenantId, UUID.fromString(organiserId))
				.orElseThrow(() -> new EntityNotFoundException("Extra not found"));

		ticket.getExtras().add(extra);
		ticketRepository.save(ticket);
		extra.getTickets().add(ticket);
		extraRepository.save(extra);

		return getTicket(ticket.getId().toString(), tenantId);
	}

}
