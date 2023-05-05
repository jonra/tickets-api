package com.tickets.api.model;

import com.tickets.api.entity.TenantEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Builder
@Data
public class TenantResponse {
	String id;
	String name;
	String host;
	String issuer;
	CountryResponse country;
	public static TenantResponse fromEntity(TenantEntity tenantEntity) {
		return Optional.ofNullable(
				tenantEntity
		).map(
				tenant -> TenantResponse.builder()
						.id(tenant.getId().toString())
						.name(tenant.getName())
						.host(tenant.getHost())
						.issuer(tenant.getIssuer())
						.country(CountryResponse.fromEntity(tenant.getCountry()))
						.build()
		).orElse(null);
	}
}
