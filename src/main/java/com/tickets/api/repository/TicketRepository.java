package com.tickets.api.repository;

import com.tickets.api.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<TicketEntity, UUID> {
	Optional<TicketEntity> findByIdAndTenantId(UUID ticketId, String tenantId);
	Optional<TicketEntity> findByIdAndEventIdAndTenantId(UUID ticketId, UUID eventId, String tenantId);
}
