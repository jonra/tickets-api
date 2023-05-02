package com.tickets.api.repository;

import com.tickets.api.entity.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TenantRepository extends JpaRepository<TenantEntity, UUID> {
	Optional<TenantEntity> findByHost(String clientHost);

}
