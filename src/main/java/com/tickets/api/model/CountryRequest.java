package com.tickets.api.model;

import com.tickets.api.entity.CountryEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Builder
@Data

public class CountryRequest {
	String name;
	String isoCode;
	String timezone;
	String phoneCode;
	List<CityRequest> cities;


	public static CountryEntity toEntity(CountryRequest country) {
		return Optional.ofNullable(country).map(e -> CountryEntity.builder()
						.name(country.getName())
						.isoAlpha2Code(country.getIsoCode())
						.timezone(country.getTimezone())
						.phoneCode(country.getPhoneCode())
						.build())
				.orElse(null);
	}
}
