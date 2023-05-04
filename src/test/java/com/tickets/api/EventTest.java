package com.tickets.api;

import com.tickets.api.auth.AuthenticationResponse;
import com.tickets.api.enums.EventType;
import com.tickets.api.enums.OrganiserType;
import com.tickets.api.model.EventRequest;
import com.tickets.api.model.EventResponse;
import com.tickets.api.model.OrganiserRequest;
import com.tickets.api.model.OrganiserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.tickets.api.TestHelper.createEvent;
import static com.tickets.api.TestHelper.createOrganiser;
import static com.tickets.api.TestHelper.init;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = ApiApplication.class)
	class EventTest extends com.tickets.api.RestAssuredBaseTestClass {


	@Test
	void create_event() {
		AuthenticationResponse user = init();

		OrganiserResponse organiser = createOrganiser(OrganiserRequest.builder()
				.name("test organiser")
				.type(OrganiserType.ARTIST)
				.build(), user.getToken());

		EventRequest eventRequest = EventRequest.builder()
					.name("test event")
					.type(EventType.MUSIC)
					.build();
		EventResponse event = createEvent(eventRequest, organiser.getId(), user.getToken());
		assert event.getId() != null;
		assert event.getName().equals(eventRequest.getName());
		assert event.getType().equals(eventRequest.getType());
	}


}
