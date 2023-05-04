package com.tickets.api;

import com.tickets.api.auth.AuthenticationResponse;
import com.tickets.api.enums.EventType;
import com.tickets.api.enums.OrganiserType;
import com.tickets.api.enums.TicketType;
import com.tickets.api.model.EventRequest;
import com.tickets.api.model.EventResponse;
import com.tickets.api.model.OrganiserRequest;
import com.tickets.api.model.OrganiserResponse;
import com.tickets.api.model.TicketRequest;
import com.tickets.api.model.TicketResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.tickets.api.TestHelper.createEvent;
import static com.tickets.api.TestHelper.createOrganiser;
import static com.tickets.api.TestHelper.createTicket;
import static com.tickets.api.TestHelper.init;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = ApiApplication.class)
	class TicketTest extends RestAssuredBaseTestClass {


	@Test
	void create_ticket() {
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

		TicketRequest ticketRequest = TicketRequest.builder()
					.name("test ticket")
					.type(TicketType.DAY)
					.build();
		TicketResponse ticket = createTicket(ticketRequest, organiser.getId(), event.getId(), user.getToken());
		assert ticket.getId() != null;
		assert ticket.getName().equals(ticketRequest.getName());
		assert ticket.getType().equals(ticketRequest.getType());
	}

}
