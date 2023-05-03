package com.tickets.api.service;

import com.tickets.api.entity.EventEntity;
import com.tickets.api.entity.ExtraEntity;
import com.tickets.api.entity.OrganiserEntity;
import com.tickets.api.exceptions.EntityNotFoundException;
import com.tickets.api.model.ExtraRequest;
import com.tickets.api.model.EventRequest;
import com.tickets.api.model.EventResponse;
import com.tickets.api.repository.EventRepository;
import com.tickets.api.repository.ExtraRepository;
import com.tickets.api.repository.OrganiserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {
	private final EventRepository eventRepository;
	private final OrganiserRepository organiserRepository;
	private final ExtraRepository extraRepository;

	public EventResponse createEvent(EventRequest deliveryPolygonRequest, String organiserId, String tenant) {
		OrganiserEntity organiser = organiserRepository.findByIdAndTenantId(UUID.fromString(organiserId), tenant)
				.orElseThrow(() -> new EntityNotFoundException("Organiser not found"));

		EventEntity eventEntity = EventRequest
				.toEntity(deliveryPolygonRequest, tenant);
		eventEntity.setOrganiser(organiser);
		EventEntity save = eventRepository.save(eventEntity);

		organiser.getEvents().add(save);
		organiserRepository.save(organiser);
		return EventResponse.fromEntity(save);
	}

	public EventResponse getEvent(String eventId, String tenant) {
		EventEntity save = eventRepository.findByIdAndTenantId(UUID.fromString(eventId), tenant)
				.orElseThrow(() -> new EntityNotFoundException("Event not found"));
		return EventResponse.fromEntity(save);
	}

	public EventResponse addExtraToEvent(ExtraRequest extraRequest, String eventId, String tenantId) {
		EventEntity event = eventRepository.findByIdAndTenantId(UUID.fromString(eventId), tenantId)
				.orElseThrow(() -> new EntityNotFoundException("Event not found"));

		ExtraEntity extraEntity = ExtraRequest.toEntity(extraRequest, tenantId);
		extraEntity.setEvent(event);

		ExtraEntity save = extraRepository.save(extraEntity);
		return getEvent(save.getId().toString(), tenantId);
	}
}
