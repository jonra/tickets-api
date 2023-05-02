package com.tickets.api.service;

import com.tickets.api.entity.EventEntity;
import com.tickets.api.entity.OrganiserEntity;
import com.tickets.api.exceptions.EntityNotFoundException;
import com.tickets.api.model.EventRequest;
import com.tickets.api.model.EventResponse;
import com.tickets.api.repository.EventRepository;
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


}
