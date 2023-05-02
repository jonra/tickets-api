package com.tickets.api.repository;

import com.tickets.api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
	Optional<UserEntity> findByIdAndTenantId(UUID userId, String tenantId);

	Optional<UserEntity> findByEmail(String email);
}
