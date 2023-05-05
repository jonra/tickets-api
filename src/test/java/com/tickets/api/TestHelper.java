package com.tickets.api;

import com.tickets.api.auth.AuthenticationController;
import com.tickets.api.auth.AuthenticationRequest;
import com.tickets.api.auth.AuthenticationResponse;
import com.tickets.api.auth.RegisterRequest;
import com.tickets.api.controller.admin.CountryController;
import com.tickets.api.controller.admin.TenantController;
import com.tickets.api.controller.client.UserController;
import com.tickets.api.controller.organiser.EventController;
import com.tickets.api.controller.organiser.ExtraController;
import com.tickets.api.controller.organiser.OrganiserController;
import com.tickets.api.controller.organiser.TicketController;
import com.tickets.api.enums.Role;
import com.tickets.api.model.CityRequest;
import com.tickets.api.model.CityResponse;
import com.tickets.api.model.CountryRequest;
import com.tickets.api.model.CountryResponse;
import com.tickets.api.model.EventRequest;
import com.tickets.api.model.EventResponse;
import com.tickets.api.model.ExtraRequest;
import com.tickets.api.model.ExtraResponse;
import com.tickets.api.model.OrganiserRequest;
import com.tickets.api.model.OrganiserResponse;
import com.tickets.api.model.TenantRequest;
import com.tickets.api.model.TenantResponse;
import com.tickets.api.model.TicketRequest;
import com.tickets.api.model.TicketResponse;
import com.tickets.api.model.UserResponse;
import com.tickets.api.model.UserRoleRequest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;

public class TestHelper  {

	public static final String EMAIL = "test@user.com";
	public static final String PASSWORD = "password";

	public static AuthenticationResponse init() {
		createTenant(TenantRequest.builder()
				.host("127.0.0.1")
				.name("local")
				.issuer("tickets-api")
				.build(), null);


		RegisterRequest userRequest = RegisterRequest.builder()
				.email(EMAIL)
				.password(PASSWORD)
				.firstName("Jon")
				.lastName("Doe")
				.build();

		AuthenticationResponse user = createUser(userRequest);

		return user;
	}

	public static UserResponse addRoleToUser(String userId, Role role, String token) {
		UserResponse userResponse = addRoleToUser(UserRoleRequest.builder().role(role).build(), userId, token);

		return userResponse;
	}

	public static TenantResponse createTenant(TenantRequest tenantRequest, String token) {
		return given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(tenantRequest)
				.post(TenantController.PATH)
				.then()
				.statusCode(200)
				.extract()
				.as(TenantResponse.class);
	}

	public static OrganiserResponse createOrganiser(OrganiserRequest organiserRequest, String token) {
		return given()
				.auth()
				.oauth2(token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(organiserRequest)
				.post(OrganiserController.PATH)
				.then()
				.statusCode(200)
				.extract()
				.as(OrganiserResponse.class);
	}

	public static OrganiserResponse addUserToOrganiser(String userId, String organiserId, String token) {
		return given()
				.auth()
				.oauth2(token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.put(replacePlaceholders(OrganiserController.PATH + "/{organiserId}/users/{userId}", organiserId, userId))
				.then()
				.statusCode(200)
				.extract()
				.as(OrganiserResponse.class);
	}

	public static EventResponse createEvent(EventRequest eventRequest, String organiserId, String token) {
		return given()
				.auth()
				.oauth2(token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(eventRequest)
				.post(replacePlaceholders(EventController.PATH, organiserId))
				.then()
				.statusCode(200)
				.extract()
				.as(EventResponse.class);
	}

	public static EventResponse addExtraToEvent(String organiserId, String eventId, String extraId, String token) {
		return given()
				.auth()
				.oauth2(token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.put(replacePlaceholders(EventController.PATH + "/{eventId}/extras/{extraId}", organiserId, eventId, extraId))
				.then()
				.statusCode(200)
				.extract()
				.as(EventResponse.class);
	}




	// Tickets
	public static TicketResponse createTicket(TicketRequest ticketRequest, String organiserId, String eventId, String token) {
		return given()
				.auth()
				.oauth2(token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(ticketRequest)
				.post(replacePlaceholders(TicketController.PATH, organiserId, eventId))
				.then()
				.statusCode(200)
				.extract()
				.as(TicketResponse.class);
	}

	public static TicketResponse addExtraTicket(String organiserId, String eventId, String ticketId, String extraId, String token) {
		return given()
				.auth()
				.oauth2(token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.put(replacePlaceholders(TicketController.PATH + "/{ticketId}/extras/{extraId}", organiserId, eventId, ticketId, extraId))
				.then()
				.statusCode(200)
				.extract()
				.as(TicketResponse.class);
	}

	// Extra
	public static ExtraResponse createExtra(ExtraRequest extraRequest, String organiserId, String token) {
		return given()
				.auth()
				.oauth2(token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(extraRequest)
				.post(replacePlaceholders(ExtraController.PATH + "organisers/{organiserId}/extras", organiserId))
				.then()
				.statusCode(200)
				.extract()
				.as(ExtraResponse.class);
	}

	public static List<ExtraResponse> getExtras(String organiserId, String token) {
		return given()
				.auth()
				.oauth2(token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.get(replacePlaceholders(ExtraController.PATH + "organisers/{organiserId}/extras", organiserId))
				.then()
				.statusCode(200)
				.extract()
				.body().jsonPath()
				.getList("", ExtraResponse.class);
	}


	public static AuthenticationResponse createUser(RegisterRequest userRequest) {
		return given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(userRequest)
				.post(AuthenticationController.PATH + "/register")
				.then()
				.statusCode(200)
				.extract()
				.as(AuthenticationResponse.class);
	}

	public static AuthenticationResponse login(AuthenticationRequest userRequest) {
		return given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(userRequest)
				.post(AuthenticationController.PATH + "/authentication")
				.then()
				.statusCode(200)
				.extract()
				.as(AuthenticationResponse.class);
	}



	// Country
	public static CountryResponse createCountry(CountryRequest countryRequest, String token) {
		return given()
				.auth()
				.oauth2(token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(countryRequest)
				.post(CountryController.PATH)
				.then()
				.statusCode(200)
				.extract()
				.as(CountryResponse.class);
	}

	public static CountryResponse addCityToCountry(String countryId, CityRequest cityRequest, String token) {
		return given()
				.auth()
				.oauth2(token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(cityRequest)
				.put(replacePlaceholders(CountryController.PATH + "/{countryId}/cities", countryId))
				.then()
				.statusCode(200)
				.extract()
				.as(CountryResponse.class);
	}

	public static List<CountryResponse> getCountries(String token) {
		return given()
				.auth()
				.oauth2(token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.get(CountryController.PATH)
				.then()
				.statusCode(200)
				.extract()
				.body().jsonPath()
				.getList("", CountryResponse.class);
	}

	public static List<CityResponse> getCities(String countryId, String token) {
		return given()
				.auth()
				.oauth2(token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.get(replacePlaceholders(CountryController.PATH + "/{countryId}/cities", countryId))
				.then()
				.statusCode(200)
				.extract()
				.body().jsonPath()
				.getList("", CityResponse.class);
	}

	public static UserResponse addRoleToUser(UserRoleRequest userRequest, String userId, String token) {
		return given()
				.auth()
				.oauth2(token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.auth()
				.oauth2(token)
				.body(userRequest)
				.put(replacePlaceholders(UserController.PATH + "/{userId}/roles", userId))
				.then()
				.statusCode(200)
				.extract()
				.as(UserResponse.class);
	}

	public static String replacePlaceholders(String url, String... values) {
		int startIndex = url.indexOf("{");
		int i = 0;
		while (startIndex != -1 && i < values.length) {
			int endIndex = url.indexOf("}", startIndex + 1);
			if (endIndex != -1) {
				String placeholder = url.substring(startIndex + 1, endIndex);
				String replacement = values[i++];
				url = url.substring(0, startIndex) + replacement + url.substring(endIndex + 1);
				endIndex = startIndex + replacement.length();
				startIndex = url.indexOf("{", endIndex);
			} else {
				startIndex = -1;
			}
		}
		return url;
	}
}
