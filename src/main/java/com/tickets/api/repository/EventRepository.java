package com.tickets.api.repository;

import com.tickets.api.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {
	Optional<EventEntity> findByIdAndTenantId(UUID eventId, String tenantId);
}
