package com.tickets.api;

import com.tickets.api.auth.AuthenticationRequest;
import com.tickets.api.auth.AuthenticationResponse;
import com.tickets.api.enums.Role;
import com.tickets.api.model.CityRequest;
import com.tickets.api.model.CityResponse;
import com.tickets.api.model.CountryRequest;
import com.tickets.api.model.CountryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.tickets.api.TestHelper.EMAIL;
import static com.tickets.api.TestHelper.PASSWORD;
import static com.tickets.api.TestHelper.addCityToCountry;
import static com.tickets.api.TestHelper.addRoleToUser;
import static com.tickets.api.TestHelper.getCities;
import static com.tickets.api.TestHelper.getCountries;
import static com.tickets.api.TestHelper.init;
import static com.tickets.api.TestHelper.login;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = ApiApplication.class)
	class CountryTest extends RestAssuredBaseTestClass {


	@Test
	void create_country() {
		AuthenticationResponse user = init();
		addRoleToUser(user.getId(), Role.TENANT_ADMIN, user.getToken());
		AuthenticationResponse login = login(AuthenticationRequest.builder().email(EMAIL).password(PASSWORD).build());

		CountryRequest countryRequest = CountryRequest.builder()
					.name("country name")
					.isoCode("IS")
					.phoneCode("123")
					.timezone("UTC")
					.build();
		CountryResponse countryResponse = TestHelper.createCountry(countryRequest, login.getToken());
		assert countryResponse.getId() != null;
		assert countryResponse.getName().equals(countryRequest.getName());
		assert countryResponse.getIsoCode().equals(countryRequest.getIsoCode());
		assert countryResponse.getPhoneCode().equals(countryRequest.getPhoneCode());
		assert countryResponse.getTimezone().equals(countryRequest.getTimezone());
	}

	@Test
	void create_country_with_city() {
		AuthenticationResponse user = init();
		addRoleToUser(user.getId(), Role.TENANT_ADMIN, user.getToken());
		AuthenticationResponse login = login(AuthenticationRequest.builder().email(EMAIL).password(PASSWORD).build());

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

		CountryResponse countryResponse = TestHelper.createCountry(countryRequest, login.getToken());
		assert countryResponse.getId() != null;
		assert countryResponse.getName().equals(countryRequest.getName());
		assert countryResponse.getIsoCode().equals(countryRequest.getIsoCode());
		assert countryResponse.getPhoneCode().equals(countryRequest.getPhoneCode());
		assert countryResponse.getTimezone().equals(countryRequest.getTimezone());
		assert countryResponse.getCities().size() == 1;
	}

	@Test
	void create_country_and_add_city() {
		AuthenticationResponse user = init();
		addRoleToUser(user.getId(), Role.TENANT_ADMIN, user.getToken());
		AuthenticationResponse login = login(AuthenticationRequest.builder().email(EMAIL).password(PASSWORD).build());

		CountryRequest countryRequest = CountryRequest.builder()
				.name("country name")
				.isoCode("IS")
				.phoneCode("123")
				.timezone("UTC")
				.build();
		CountryResponse countryResponse = TestHelper.createCountry(countryRequest, login.getToken());

		CountryResponse countryResponse1 = addCityToCountry(countryResponse.getId(), CityRequest.builder().name("city name 1").build(), login.getToken());
		CountryResponse countryResponse2 = addCityToCountry(countryResponse.getId(), CityRequest.builder().name("city name 2").build(), login.getToken());
		assert countryResponse2.getCities().size() == 2;

		List<CountryResponse> countries = getCountries(user.getToken());
		assert countries.size() == 1;
	}

	@Test
	void create_countries() {
		AuthenticationResponse user = init();
		addRoleToUser(user.getId(), Role.TENANT_ADMIN, user.getToken());
		AuthenticationResponse login = login(AuthenticationRequest.builder().email(EMAIL).password(PASSWORD).build());

		createCountry("Country name 1", "ISO1", "City name 1", login.getToken());
		createCountry("Country name 2", "ISO2", "City name 2", login.getToken());
		createCountry("Country name 3", "ISO3", "City name 3", login.getToken());

		List<CountryResponse> countries = getCountries(user.getToken());
		assert countries.size() == 3;
	}

	@Test
	void get_cities_for_country() {
		AuthenticationResponse user = init();
		addRoleToUser(user.getId(), Role.TENANT_ADMIN, user.getToken());
		AuthenticationResponse login = login(AuthenticationRequest.builder().email(EMAIL).password(PASSWORD).build());

		CountryResponse country = createCountry("Country name 1", "ISO1", "City name 1", login.getToken());

		List<CityResponse> cities = getCities(country.getId(), user.getToken());
		assert cities.size() == 1;
	}

	private CountryResponse createCountry(String name, String iso, String cityName, String token) {
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
		return TestHelper.createCountry(countryRequest, token);


	}

}
