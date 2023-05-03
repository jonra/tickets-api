package com.tickets.api.service;

import com.tickets.api.entity.ExtraEntity;
import com.tickets.api.entity.OrganiserEntity;
import com.tickets.api.exceptions.EntityNotFoundException;
import com.tickets.api.model.ExtraRequest;
import com.tickets.api.model.ExtraResponse;
import com.tickets.api.repository.ExtraRepository;
import com.tickets.api.repository.OrganiserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExtraService {
	private final OrganiserRepository organiserRepository;
	private final ExtraRepository extraRepository;

	public ExtraResponse createExtra(ExtraRequest extraRequest, String organiserId, String tenantId) {
		OrganiserEntity organiser = organiserRepository.findByIdAndTenantId(UUID.fromString(organiserId), tenantId)
				.orElseThrow(() -> new EntityNotFoundException("Event not found"));

		ExtraEntity extraEntity = ExtraRequest
				.toEntity(extraRequest, tenantId);
		extraEntity.setOrganiser(organiser);
		ExtraEntity save = extraRepository.save(extraEntity);

		return getExtra(save.getId().toString(), tenantId);
	}

	public ExtraResponse getExtra(String ticketId, String tenant) {
		ExtraEntity save = extraRepository.findByIdAndTenantId(UUID.fromString(ticketId), tenant)
				.orElseThrow(() -> new EntityNotFoundException("Extra not found"));
		return ExtraResponse.fromEntity(save);
	}

	public List<ExtraResponse> getExtras(String organiserId, String tenantId) {
		List<ExtraEntity> extras = extraRepository.findAllByOrganiserIdAndTenantId(UUID.fromString(organiserId), tenantId);
		return ExtraResponse.fromEntity(extras);
	}

	//	public TicketResponse addExtraToTicket(String tenantId, String organiserId, String eventId, String extraId) {
//		EventEntity event = eventRepository.findByIdAndTenantIdAndOrganiserId(UUID.fromString(eventId), tenantId, UUID.fromString(organiserId))
//				.orElseThrow(() -> new EntityNotFoundException("Event not found"));
//
//		TicketEntity ticket = ticketRepository.findByIdAndTenantId(UUID.fromString(extraId), tenantId)
//				.orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
//
//		ExtraEntity extra = extraRepository.findByIdAndTenantIdAndOrganiserId(UUID.fromString(extraId), tenantId, organiserId)
//				.orElseThrow(() -> new EntityNotFoundException("Extra not found"));
//
//		ticket.getExtras().add(extra);
//		ticketRepository.save(ticket);
//
//		return getTicket(ticket.getId().toString(), tenantId);
//	}

}
