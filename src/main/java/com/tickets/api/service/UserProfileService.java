package com.tickets.api.service;

import com.tickets.api.entity.UserEntity;
import com.tickets.api.exceptions.EntityNotFoundException;
import com.tickets.api.model.UserRequest;
import com.tickets.api.model.UserResponse;
import com.tickets.api.model.UserRoleRequest;
import com.tickets.api.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileService {
	private final UserProfileRepository userProfileRepository;
	public UserResponse createUser(UserRequest userRequest, String tenantId) {
		UserEntity tenant = userProfileRepository.save(UserRequest.toEntity(userRequest, tenantId));
		return UserResponse.fromEntity(tenant);
	}
	public UserResponse addRoleToUser(UserRoleRequest userRequest, String userId, String tenantId) {
		UserEntity user = userProfileRepository.findByIdAndTenantId(UUID.fromString(userId), tenantId)
				.orElseThrow(() -> new EntityNotFoundException("User not found"));

		user.getRoles().add(userRequest.getRole());
		UserEntity save = userProfileRepository.save(user);

		return UserResponse.fromEntity(save);
	}

	public UserResponse getUser(String userId, String id) {
		UserEntity user = userProfileRepository.findByIdAndTenantId(UUID.fromString(userId), id)
				.orElseThrow(() -> new EntityNotFoundException("User not found"));

		return UserResponse.fromEntity(user);
	}
}
