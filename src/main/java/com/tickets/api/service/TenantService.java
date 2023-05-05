package com.tickets.api.service;

import com.tickets.api.entity.TenantEntity;
import com.tickets.api.exceptions.AuthenticationException;
import com.tickets.api.model.TenantRequest;
import com.tickets.api.model.TenantResponse;
import com.tickets.api.repository.CountryRepository;
import com.tickets.api.repository.EventRepository;
import com.tickets.api.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.tickets.api.model.TenantResponse.fromEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantService {
	private final CountryRepository countryRepository;
	private final EventRepository eventRepository;
	private final TenantRepository tenantRepository;

	public TenantResponse getTenant(String clientHost) {
		return tenantRepository.findByHost(clientHost)
				.map(x -> fromEntity(x))
				.orElseThrow(() -> new AuthenticationException("Tenant not found for client host: " + clientHost));
	}

	public TenantResponse createTenant(TenantRequest tenantRequest) {
		TenantEntity tenant = tenantRepository.save(TenantRequest.toEntity(tenantRequest));
		return TenantResponse.fromEntity(tenant);
	}

	public TenantResponse mergeTenant(String tenantId, TenantRequest tenantRequest) {
		TenantEntity tenantEntity = tenantRepository.findById(UUID.fromString(tenantId))
				.orElseThrow(() -> new AuthenticationException("Tenant not found for id: " + tenantId));

		Optional.ofNullable(tenantRequest.getName()).ifPresent(tenantEntity::setName);
		Optional.ofNullable(tenantRequest.getHost()).ifPresent(tenantEntity::setHost);
		Optional.ofNullable(tenantRequest.getIssuer()).ifPresent(tenantEntity::setIssuer);
		Optional.ofNullable(tenantRequest.getCountryId()).ifPresent(x -> tenantEntity.setCountry(countryRepository.findById(UUID.fromString(x))
				.orElseThrow(() -> new AuthenticationException("Country not found for id: " + x))));

		TenantEntity save = tenantRepository.save(tenantEntity);
		return fromEntity(save);
	}
}
