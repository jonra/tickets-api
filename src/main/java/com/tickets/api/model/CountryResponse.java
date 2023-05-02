package com.tickets.api.model;

import com.tickets.api.entity.CountryEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data

public class CountryResponse {
	String id;
	String name;
	String isoCode;
	String timezone;
	String phoneCode;
	List<CityResponse> cities;

	public static List<CountryResponse> fromEntity(List<CountryEntity> countryEntities) {
		if (countryEntities == null) return null;
		return countryEntities.stream().map(CountryResponse::fromEntity).collect(Collectors.toList());
	}

	public static CountryResponse fromEntity(CountryEntity countryEntity) {
		return Optional.ofNullable(
				countryEntity
		).map(
				country -> CountryResponse.builder()
						.id(country.getId().toString())
						.name(country.getName())
						.isoCode(country.getIsoAlpha2Code())
						.timezone(country.getTimezone())
						.phoneCode(country.getPhoneCode())
						.cities(CityResponse.fromEntity(country.getCities()))
						.build()
		).orElse(null);
	}
}
