package com.tickets.api.model;

import com.tickets.api.entity.CityEntity;
import com.tickets.api.entity.CountryEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data

public class CityRequest {
	@NotNull String name;
	String timezone;

	public static List<CityEntity> toEntity(List<CityRequest> cities, CountryEntity country) {
		if (cities == null) return null;

		return cities.stream().map(e -> toEntity(e, country)).collect(java.util.stream.Collectors.toList());
	}
	public static CityEntity toEntity(CityRequest city, CountryEntity country) {
		if (city == null) return null;

		return CityEntity.builder()
				.name(city.getName())
				.timezone(city.getTimezone())
				.country(country)
				.build();
	}
}
