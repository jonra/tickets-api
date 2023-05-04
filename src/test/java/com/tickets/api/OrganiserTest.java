package com.tickets.api;

import com.tickets.api.enums.OrganiserType;
import com.tickets.api.model.OrganiserRequest;
import com.tickets.api.model.OrganiserResponse;
import com.tickets.api.model.TenantRequest;
import com.tickets.api.model.UserRequest;
import com.tickets.api.model.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.tickets.api.TestHelper.addUserToOrganiser;
import static com.tickets.api.TestHelper.createOrganiser;
import static com.tickets.api.TestHelper.createTenant;
import static com.tickets.api.TestHelper.createUser;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = ApiApplication.class)
	class OrganiserTest extends RestAssuredBaseTestClass {

	@Test
	void create_organiser() {
		createTenant(TenantRequest.builder().host("127.0.0.1").name("local").build());

		OrganiserRequest organiser = OrganiserRequest.builder()
				.name("test organiser")
				.type(OrganiserType.ARTIST)
				.build();

		OrganiserResponse event = createOrganiser(organiser);
		assert event.getId() != null;
		assert event.getName().equals(organiser.getName());
		assert event.getType().equals(organiser.getType());
	}

	@Test
	void create_event_and_add_user() {
		createTenant(TenantRequest.builder().host("127.0.0.1").name("local").build());

		OrganiserResponse organiser = createOrganiser(OrganiserRequest.builder()
				.name("test organiser")
				.type(OrganiserType.ARTIST)
				.build());


		UserRequest userRequest = UserRequest.builder()
				.email("jon@test.com")
				.password("123456")
				.firstName("Jon")
				.lastName("Doe")
				.build();

		UserResponse user = createUser(userRequest);

		OrganiserResponse withUser = addUserToOrganiser(user.getId(), organiser.getId());
		assert withUser.getUsers().size() == 1;

	}
}
