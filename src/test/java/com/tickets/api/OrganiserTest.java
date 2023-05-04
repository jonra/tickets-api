package com.tickets.api;

import com.tickets.api.auth.AuthenticationResponse;
import com.tickets.api.enums.OrganiserType;
import com.tickets.api.model.OrganiserRequest;
import com.tickets.api.model.OrganiserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.tickets.api.TestHelper.addUserToOrganiser;
import static com.tickets.api.TestHelper.createOrganiser;
import static com.tickets.api.TestHelper.init;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = ApiApplication.class)
	class OrganiserTest extends RestAssuredBaseTestClass {

	@Test
	void create_organiser() {
				AuthenticationResponse user = init();


		OrganiserRequest organiser = OrganiserRequest.builder()
				.name("test organiser")
				.type(OrganiserType.ARTIST)
				.build();

		OrganiserResponse event = createOrganiser(organiser, user.getToken());
		assert event.getId() != null;
		assert event.getName().equals(organiser.getName());
		assert event.getType().equals(organiser.getType());
	}

	@Test
	void create_event_and_add_user() {
		AuthenticationResponse user = init();


		OrganiserResponse organiser = createOrganiser(OrganiserRequest.builder()
				.name("test organiser")
				.type(OrganiserType.ARTIST)
				.build(), user.getToken());

		OrganiserResponse withUser = addUserToOrganiser(user.getId(), organiser.getId(), user.getToken());
		assert withUser.getUsers().size() == 1;

	}
}
