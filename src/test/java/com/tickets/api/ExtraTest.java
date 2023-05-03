package com.tickets.api;

import com.tickets.api.enums.EventType;
import com.tickets.api.enums.ExtraType;
import com.tickets.api.enums.OrganiserType;
import com.tickets.api.enums.TicketType;
import com.tickets.api.model.EventRequest;
import com.tickets.api.model.EventResponse;
import com.tickets.api.model.ExtraRequest;
import com.tickets.api.model.ExtraResponse;
import com.tickets.api.model.OrganiserRequest;
import com.tickets.api.model.OrganiserResponse;
import com.tickets.api.model.TenantRequest;
import com.tickets.api.model.TicketRequest;
import com.tickets.api.model.TicketResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static com.tickets.api.TestHelper.addExtraTicket;
import static com.tickets.api.TestHelper.createEvent;
import static com.tickets.api.TestHelper.createExtra;
import static com.tickets.api.TestHelper.createOrganiser;
import static com.tickets.api.TestHelper.createTenant;
import static com.tickets.api.TestHelper.createTicket;
import static com.tickets.api.TestHelper.getExtras;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = ApiApplication.class)
	class ExtraTest extends RestAssuredBaseTestClass {


	@Test
	void create_extra() {
		createTenant(TenantRequest.builder().host("127.0.0.1").name("local").build());

		OrganiserResponse organiser = createOrganiser(OrganiserRequest.builder()
				.name("test organiser")
				.type(OrganiserType.ARTIST)
				.build());

		// Create generic extra linked to organiser
		ExtraRequest extraRequest = ExtraRequest.builder()
				.name("test extra")
				.type(ExtraType.ACCOMMODATION)
				.build();

		ExtraResponse extra = createExtra(extraRequest, organiser.getId());
		assert extra.getId() != null;
		assert extra.getName().equals(extraRequest.getName());
		assert extra.getType().equals(extraRequest.getType());

		List<ExtraResponse> extras = getExtras(organiser.getId());
		assert extras.size() == 1;
		assert extras.get(0).getId().equals(extra.getId());
		assert extras.get(0).getName().equals(extra.getName());
		assert extras.get(0).getType().equals(extra.getType());

	}

	@Test
	void add_extra_to_ticket() {
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

		// Create generic extra linked to organiser
		ExtraRequest extraRequest = ExtraRequest.builder()
				.name("test extra")
				.type(ExtraType.ACCOMMODATION)
				.price(BigDecimal.valueOf(100.0))
				.build();

		ExtraResponse extra = createExtra(extraRequest, organiser.getId());

		TicketResponse ticketResponse = addExtraTicket(organiser.getId(), event.getId(), ticket.getId(), extra.getId());
		assert ticketResponse.getExtras().size() == 1;
		assert ticketResponse.getExtras().get(0).getId().equals(extra.getId());
		assert ticketResponse.getExtras().get(0).getName().equals(extra.getName());
		assert ticketResponse.getExtras().get(0).getType().equals(extra.getType());
		assert ticketResponse.getExtras().get(0).getPrice().intValue() == (extra.getPrice()).intValue();
	}
}
