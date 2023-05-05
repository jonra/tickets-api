package com.tickets.api.model;

import com.tickets.api.entity.TenantEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Builder
@Data

public class TenantRequest {
	String host;
	String name;
	String issuer;

	public static List<TenantEntity> toEntity(List<TenantRequest> eventList) {
		return Optional.ofNullable(eventList)
				.map(e -> toEntity(e))
				.orElse(null);

	}

	public static TenantEntity toEntity(TenantRequest event) {
		return Optional.ofNullable(event).map(e -> TenantEntity.builder()
						.name(event.getName())
						.host(event.getHost())
						.issuer(event.getIssuer())
						.build())
				.orElse(null);
	}
}
