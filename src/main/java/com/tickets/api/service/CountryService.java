package com.tickets.api.service;

import com.tickets.api.entity.CityEntity;
import com.tickets.api.entity.CountryEntity;
import com.tickets.api.exceptions.EntityNotFoundException;
import com.tickets.api.model.CityRequest;
import com.tickets.api.model.CityResponse;
import com.tickets.api.model.CountryRequest;
import com.tickets.api.model.CountryResponse;
import com.tickets.api.repository.CityRepository;
import com.tickets.api.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryService {
	private final CountryRepository countryRepository;
	private final CityRepository cityRepository;


	public CountryResponse createCountry(CountryRequest countryRequest) {
		CountryEntity countryEntity = CountryRequest.toEntity(countryRequest);
		CountryEntity country = countryRepository.save(countryEntity);

		country.setCities(CityRequest.toEntity(countryRequest.getCities(), country));
		countryRepository.save(countryEntity);



		return CountryResponse.fromEntity(country);
	}

	public List<CountryResponse> getCountries() {
		List<CountryEntity> all = countryRepository.findAll();
		return CountryResponse.fromEntity(all);
	}

	public CountryResponse addCityToCountry(String countryId, CityRequest cityRequest) {
		CountryEntity country = countryRepository.findById(UUID.fromString(countryId))
				.orElseThrow(() -> new EntityNotFoundException("Country not found"));

		CityEntity cityEntity = CityRequest.toEntity(cityRequest, country);

		country.getCities().add(cityEntity);
		CountryEntity save = countryRepository.save(country);
//		cityRepository.save(cityEntity);

		return CountryResponse.fromEntity(save);
	}

	public List<CityResponse> getCities(String countryId) {
		List<CityEntity> allByCountryId = cityRepository.findAllByCountryId(UUID.fromString(countryId));
		return CityResponse.fromEntity(allByCountryId);

	}
}
