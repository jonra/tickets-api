package com.tickets.api;

import com.tickets.api.enums.EventType;
import com.tickets.api.enums.OrganiserType;
import com.tickets.api.model.EventRequest;
import com.tickets.api.model.EventResponse;
import com.tickets.api.model.OrganiserRequest;
import com.tickets.api.model.OrganiserResponse;
import com.tickets.api.model.TenantRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.tickets.api.TestHelper.createEvent;
import static com.tickets.api.TestHelper.createOrganiser;
import static com.tickets.api.TestHelper.createTenant;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = ApiApplication.class)
	class EventTest extends com.tickets.api.RestAssuredBaseTestClass {


	@Test
	void create_event() {
		createTenant(TenantRequest.builder().host("127.0.0.1").name("local").build());

		OrganiserResponse organiser = createOrganiser(OrganiserRequest.builder()
				.name("test organiser")
				.type(OrganiserType.ARTIST)
				.build());

		EventRequest eventRequest = EventRequest.builder()
					.name("test event")
					.type(EventType.MUSIC)
					.build();
		EventResponse event = createEvent(eventRequest, organiser.getId());
		assert event.getId() != null;
		assert event.getName().equals(eventRequest.getName());
		assert event.getType().equals(eventRequest.getType());
	}


}
