package com.tickets.api.service;

import com.tickets.api.entity.OrganiserEntity;
import com.tickets.api.entity.UserEntity;
import com.tickets.api.exceptions.EntityNotFoundException;
import com.tickets.api.model.OrganiserRequest;
import com.tickets.api.model.OrganiserResponse;
import com.tickets.api.model.OrganiserUserRequest;
import com.tickets.api.repository.ExtraRepository;
import com.tickets.api.repository.OrganiserRepository;
import com.tickets.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganiserService {
	private final OrganiserRepository organiserRepository;
	private final UserRepository userRepository;
	private final ExtraRepository extraRepository;

	public OrganiserResponse createOrganiser(OrganiserRequest deliveryPolygonRequest, String tenant) {
		OrganiserEntity eventEntity = OrganiserRequest
				.toEntity(deliveryPolygonRequest, tenant);

		OrganiserEntity save = organiserRepository.save(eventEntity);
		return OrganiserResponse.fromEntity(save);
	}

	public OrganiserResponse getOrganiser(String eventId, String tenant) {
		OrganiserEntity save = organiserRepository.findByIdAndTenantId(UUID.fromString(eventId), tenant)
				.orElseThrow(() -> new EntityNotFoundException("Organiser not found"));
		return OrganiserResponse.fromEntity(save);
	}

	public OrganiserResponse addUserToOrganiser(OrganiserUserRequest userRequest, String tenantId) {
		OrganiserEntity organiser = organiserRepository.findByIdAndTenantId(UUID.fromString(userRequest.getOrganiserId()), tenantId)
				.orElseThrow(() -> new EntityNotFoundException("Organiser not found"));

		UserEntity user = userRepository.findByIdAndTenantId(UUID.fromString(userRequest.getUserId()), tenantId)
				.orElseThrow(() -> new EntityNotFoundException("User not found"));

		organiser.getUsers().add(user);
		organiserRepository.save(organiser);


		return getOrganiser(userRequest.getOrganiserId(), tenantId);
	}

}
