package com.tickets.api.repository;

import com.tickets.api.entity.OrganiserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrganiserRepository extends JpaRepository<OrganiserEntity, UUID> {
	Optional<OrganiserEntity> findByIdAndTenantId(UUID eventId, String tenantId);
}
