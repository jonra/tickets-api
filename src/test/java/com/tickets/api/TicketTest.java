package com.tickets.api;

import com.tickets.api.enums.EventType;
import com.tickets.api.enums.OrganiserType;
import com.tickets.api.enums.TicketType;
import com.tickets.api.model.EventRequest;
import com.tickets.api.model.EventResponse;
import com.tickets.api.model.OrganiserRequest;
import com.tickets.api.model.OrganiserResponse;
import com.tickets.api.model.TenantRequest;
import com.tickets.api.model.TicketRequest;
import com.tickets.api.model.TicketResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.tickets.api.TestHelper.createEvent;
import static com.tickets.api.TestHelper.createOrganiser;
import static com.tickets.api.TestHelper.createTenant;
import static com.tickets.api.TestHelper.createTicket;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = ApiApplication.class)
	class TicketTest extends RestAssuredBaseTestClass {


	@Test
	void create_ticket() {
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

		TicketRequest ticketRequest = TicketRequest.builder()
					.name("test ticket")
					.type(TicketType.DAY)
					.build();
		TicketResponse ticket = createTicket(ticketRequest, organiser.getId(), event.getId());
		assert ticket.getId() != null;
		assert ticket.getName().equals(ticketRequest.getName());
		assert ticket.getType().equals(ticketRequest.getType());
	}

}
