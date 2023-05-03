package com.tickets.api.repository;

import com.tickets.api.entity.ExtraEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExtraRepository extends JpaRepository<ExtraEntity, UUID> {
	Optional<ExtraEntity> findByIdAndTenantId(UUID eventId, String tenantId);
	Optional<ExtraEntity> findByIdAndTenantIdAndOrganiserId(UUID eventId, String tenantId, UUID organiserId);
	List<ExtraEntity> findAllByOrganiserIdAndTenantId(UUID organiserId, String tenantId);
}
