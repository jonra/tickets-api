package com.tickets.api.model;

import com.tickets.api.entity.CityEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@Data

public class CityResponse {
	String id;
	String name;
	String timezone;

	public static List<CityResponse> fromEntity(List<CityEntity> cityEntities) {
		if (cityEntities == null) return null;
		return cityEntities.stream().map(city -> CityResponse.fromEntity(city)).collect(Collectors.toList());
	}

	public static CityResponse fromEntity(CityEntity cityEntity) {
		return Optional.ofNullable(
				cityEntity
		).map(
				city -> CityResponse.builder()
						.id(city.getId().toString())
						.name(city.getName())
						.timezone(city.getTimezone())
						.build()
		).orElse(null);
	}
}
