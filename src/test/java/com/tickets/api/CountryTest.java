package com.tickets.api;

import com.tickets.api.model.CityRequest;
import com.tickets.api.model.CityResponse;
import com.tickets.api.model.CountryRequest;
import com.tickets.api.model.CountryResponse;
import com.tickets.api.model.TenantRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.tickets.api.TestHelper.addCityToCountry;
import static com.tickets.api.TestHelper.createTenant;
import static com.tickets.api.TestHelper.getCities;
import static com.tickets.api.TestHelper.getCountries;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = ApiApplication.class)
	class CountryTest extends RestAssuredBaseTestClass {


	@Test
	void create_country() {
		createTenant(TenantRequest.builder().host("127.0.0.1").name("local").build());

		CountryRequest countryRequest = CountryRequest.builder()
					.name("country name")
					.isoCode("IS")
					.phoneCode("123")
					.timezone("UTC")
					.build();
		CountryResponse countryResponse = TestHelper.createCountry(countryRequest);
		assert countryResponse.getId() != null;
		assert countryResponse.getName().equals(countryRequest.getName());
		assert countryResponse.getIsoCode().equals(countryRequest.getIsoCode());
		assert countryResponse.getPhoneCode().equals(countryRequest.getPhoneCode());
		assert countryResponse.getTimezone().equals(countryRequest.getTimezone());
	}

	@Test
	void create_country_with_city() {
		createTenant(TenantRequest.builder().host("127.0.0.1").name("local").build());

		CityRequest cityRequest = CityRequest.builder()
					.name("city name")
					.build();

		CountryRequest countryRequest = CountryRequest.builder()
					.name("country name")
					.isoCode("IS")
					.phoneCode("123")
					.timezone("UTC")
					.cities(List.of(cityRequest))
					.build();

		CountryResponse countryResponse = TestHelper.createCountry(countryRequest);
		assert countryResponse.getId() != null;
		assert countryResponse.getName().equals(countryRequest.getName());
		assert countryResponse.getIsoCode().equals(countryRequest.getIsoCode());
		assert countryResponse.getPhoneCode().equals(countryRequest.getPhoneCode());
		assert countryResponse.getTimezone().equals(countryRequest.getTimezone());
		assert countryResponse.getCities().size() == 1;
	}

	@Test
	void create_country_and_add_city() {
		createTenant(TenantRequest.builder().host("127.0.0.1").name("local").build());

		CountryRequest countryRequest = CountryRequest.builder()
				.name("country name")
				.isoCode("IS")
				.phoneCode("123")
				.timezone("UTC")
				.build();
		CountryResponse countryResponse = TestHelper.createCountry(countryRequest);

		CountryResponse countryResponse1 = addCityToCountry(countryResponse.getId(), CityRequest.builder().name("city name 1").build());
		CountryResponse countryResponse2 = addCityToCountry(countryResponse.getId(), CityRequest.builder().name("city name 2").build());
		assert countryResponse2.getCities().size() == 2;

		List<CountryResponse> countries = getCountries();
		assert countries.size() == 1;
	}

	@Test
	void create_countries() {
		createTenant(TenantRequest.builder().host("127.0.0.1").name("local").build());

		createCountry("Country name 1", "ISO1", "City name 1");
		createCountry("Country name 2", "ISO2", "City name 2");
		createCountry("Country name 3", "ISO3", "City name 3");

		List<CountryResponse> countries = getCountries();
		assert countries.size() == 3;
	}

	@Test
	void get_cities_for_country() {
		createTenant(TenantRequest.builder().host("127.0.0.1").name("local").build());

		CountryResponse country = createCountry("Country name 1", "ISO1", "City name 1");

		List<CityResponse> cities = getCities(country.getId());
		assert cities.size() == 1;
	}

	private CountryResponse createCountry(String name, String iso, String cityName) {
		CityRequest cityRequest = CityRequest.builder()
				.name(cityName)
				.build();

		CountryRequest countryRequest = CountryRequest.builder()
				.name("country name")
				.isoCode("IS")
				.phoneCode("123")
				.timezone("UTC")
				.cities(List.of(cityRequest))
				.build();
		return TestHelper.createCountry(countryRequest);


	}

}
