package com.tickets.api;

import com.tickets.api.controller.admin.CountryController;
import com.tickets.api.controller.admin.TenantController;
import com.tickets.api.controller.client.UserController;
import com.tickets.api.controller.organiser.EventController;
import com.tickets.api.controller.organiser.ExtraController;
import com.tickets.api.controller.organiser.OrganiserController;
import com.tickets.api.controller.organiser.TicketController;
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
import com.tickets.api.model.OrganiserUserRequest;
import com.tickets.api.model.TenantRequest;
import com.tickets.api.model.TenantResponse;
import com.tickets.api.model.TicketRequest;
import com.tickets.api.model.TicketResponse;
import com.tickets.api.model.UserRequest;
import com.tickets.api.model.UserResponse;
import com.tickets.api.model.UserRoleRequest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;

public class TestHelper  {

	public static OrganiserResponse createOrganiser(OrganiserRequest organiserRequest) {
		return given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(organiserRequest)
				.post(OrganiserController.PATH)
				.then()
				.statusCode(200)
				.extract()
				.as(OrganiserResponse.class);
	}

	public static OrganiserResponse addUserToOrganiser(OrganiserUserRequest organiserRequest) {
		return given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(organiserRequest)
				.put(OrganiserController.PATH + "/users")
				.then()
				.statusCode(200)
				.extract()
				.as(OrganiserResponse.class);
	}

	public static EventResponse createEvent(EventRequest eventRequest, String organiserId) {
		return given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(eventRequest)
				.post(replacePlaceholders(EventController.PATH + "/organisers/{organiser_id}/events", organiserId))
				.then()
				.statusCode(200)
				.extract()
				.as(EventResponse.class);
	}

	public static TenantResponse createTenant(TenantRequest tenantRequest) {
		return given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(tenantRequest)
				.post(TenantController.PATH)
				.then()
				.statusCode(200)
				.extract()
				.as(TenantResponse.class);
	}

	// Tickets
	public static TicketResponse createTicket(TicketRequest ticketRequest, String organiserId, String eventId) {
		return given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(ticketRequest)
				.post(replacePlaceholders(TicketController.PATH, organiserId, eventId))
				.then()
				.statusCode(200)
				.extract()
				.as(TicketResponse.class);
	}

	public static TicketResponse addExtraTicket(String organiserId, String eventId, String ticketId, String extraId) {
		return given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.put(replacePlaceholders(TicketController.PATH + "/{ticketId}/extras/{extraId}", organiserId, eventId, ticketId, extraId))
				.then()
				.statusCode(200)
				.extract()
				.as(TicketResponse.class);
	}

	// Extra
	public static ExtraResponse createExtra(ExtraRequest extraRequest, String organiserId) {
		return given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(extraRequest)
				.post(replacePlaceholders(ExtraController.PATH + "organisers/{organiserId}/extras", organiserId))
				.then()
				.statusCode(200)
				.extract()
				.as(ExtraResponse.class);
	}

	public static List<ExtraResponse> getExtras(String organiserId) {
		return given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.get(replacePlaceholders(ExtraController.PATH + "organisers/{organiserId}/extras", organiserId))
				.then()
				.statusCode(200)
				.extract()
				.body().jsonPath()
				.getList("", ExtraResponse.class);
	}


	public static UserResponse createUser(UserRequest userRequest) {
		return given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(userRequest)
				.post(UserController.PATH)
				.then()
				.statusCode(200)
				.extract()
				.as(UserResponse.class);
	}



	// Country
	public static CountryResponse createCountry(CountryRequest countryRequest) {
		return given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(countryRequest)
				.post(CountryController.PATH)
				.then()
				.statusCode(200)
				.extract()
				.as(CountryResponse.class);
	}

	public static CountryResponse addCityToCountry(String countryId, CityRequest cityRequest) {
		return given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(cityRequest)
				.put(replacePlaceholders(CountryController.PATH + "/{countryId}/cities", countryId))
				.then()
				.statusCode(200)
				.extract()
				.as(CountryResponse.class);
	}

	public static List<CountryResponse> getCountries() {
		return given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.get(CountryController.PATH)
				.then()
				.statusCode(200)
				.extract()
				.body().jsonPath()
				.getList("", CountryResponse.class);
	}

	public static List<CityResponse> getCities(String countryId) {
		return given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.get(replacePlaceholders(CountryController.PATH + "/{countryId}/cities", countryId))
				.then()
				.statusCode(200)
				.extract()
				.body().jsonPath()
				.getList("", CityResponse.class);
	}

	public static UserResponse addRoleToUser(UserRoleRequest userRequest, String userId) {
		return given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
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
