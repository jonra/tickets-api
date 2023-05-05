package com.tickets.api.service;

import com.tickets.api.entity.OrganiserEntity;
import com.tickets.api.entity.UserEntity;
import com.tickets.api.exceptions.EntityNotFoundException;
import com.tickets.api.model.OrganiserRequest;
import com.tickets.api.model.OrganiserResponse;
import com.tickets.api.model.UserResponse;
import com.tickets.api.repository.ExtraRepository;
import com.tickets.api.repository.OrganiserRepository;
import com.tickets.api.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganiserService {
	private final OrganiserRepository organiserRepository;
	private final UserProfileRepository userProfileRepository;
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

	public OrganiserResponse addUserToOrganiser(String organiserId, String userId, String tenantId) {
		OrganiserEntity organiser = organiserRepository.findByIdAndTenantId(UUID.fromString(organiserId), tenantId)
				.orElseThrow(() -> new EntityNotFoundException("Organiser not found"));

		UserEntity user = userProfileRepository.findByIdAndTenantId(UUID.fromString(userId), tenantId)
				.orElseThrow(() -> new EntityNotFoundException("User not found"));

		user.setOrganiser(organiser);
		UserEntity save = userProfileRepository.save(user);

		if (organiser.getUsers() == null) {
			ArrayList<UserEntity> objects = new ArrayList<>();
			objects.add(save);
			organiser.setUsers(objects);
		} else {
			organiser.getUsers().add(save);
		}
		organiserRepository.save(organiser);
		return getOrganiser(organiserId, tenantId);
	}

	public List<UserResponse> getUsersForOrganiser(String organiserId, String tenantId) {
		OrganiserEntity organiser = organiserRepository.findByIdAndTenantId(UUID.fromString(organiserId), tenantId)
				.orElseThrow(() -> new EntityNotFoundException("Organiser not found"));

		List<UserEntity> users = organiser.getUsers();

		return UserResponse.fromEntity(users);
	}
}
