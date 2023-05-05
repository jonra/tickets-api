package com.tickets.api;

import com.tickets.api.auth.AuthenticationResponse;
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
import com.tickets.api.model.TicketRequest;
import com.tickets.api.model.TicketResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static com.tickets.api.TestHelper.addExtraTicket;
import static com.tickets.api.TestHelper.addExtraToEvent;
import static com.tickets.api.TestHelper.createEvent;
import static com.tickets.api.TestHelper.createExtra;
import static com.tickets.api.TestHelper.createOrganiser;
import static com.tickets.api.TestHelper.createTicket;
import static com.tickets.api.TestHelper.getExtras;
import static com.tickets.api.TestHelper.init;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = ApiApplication.class)
	class ExtraTest extends RestAssuredBaseTestClass {


	@Test
	void create_extra() {
		AuthenticationResponse user = init();

		OrganiserResponse organiser = createOrganiser(OrganiserRequest.builder()
				.name("test organiser")
				.type(OrganiserType.ARTIST)
				.build(), user.getToken());

		// Create generic extra linked to organiser
		ExtraRequest extraRequest = ExtraRequest.builder()
				.name("test extra")
				.type(ExtraType.ACCOMMODATION)
				.build();

		ExtraResponse extra = createExtra(extraRequest, organiser.getId(), user.getToken());
		assert extra.getId() != null;
		assert extra.getName().equals(extraRequest.getName());
		assert extra.getType().equals(extraRequest.getType());

		List<ExtraResponse> extras = getExtras(organiser.getId(), user.getToken());
		assert extras.size() == 1;
		assert extras.get(0).getId().equals(extra.getId());
		assert extras.get(0).getName().equals(extra.getName());
		assert extras.get(0).getType().equals(extra.getType());

	}

	@Test
	void add_extra_to_ticket() {
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
				.ticketType(TicketType.DAY)
				.build();
		TicketResponse ticket = createTicket(ticketRequest, organiser.getId(), event.getId(), user.getToken());

		// Create generic extra linked to organiser
		ExtraRequest extraRequest = ExtraRequest.builder()
				.name("test extra")
				.type(ExtraType.ACCOMMODATION)
				.price(BigDecimal.valueOf(100.0))
				.build();

		ExtraResponse extra = createExtra(extraRequest, organiser.getId(), user.getToken());

		TicketResponse ticketResponse = addExtraTicket(organiser.getId(), event.getId(), ticket.getId(), extra.getId(), user.getToken());
		assert ticketResponse.getExtras().size() == 1;
		assert ticketResponse.getExtras().get(0).getId().equals(extra.getId());
		assert ticketResponse.getExtras().get(0).getName().equals(extra.getName());
		assert ticketResponse.getExtras().get(0).getType().equals(extra.getType());
		assert ticketResponse.getExtras().get(0).getPrice().intValue() == (extra.getPrice()).intValue();
	}

	@Test
	void add_extra_to_event() {
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

		// Create generic extra linked to organiser
		ExtraRequest extraRequest = ExtraRequest.builder()
				.name("test extra")
				.type(ExtraType.ACCOMMODATION)
				.price(BigDecimal.valueOf(100.0))
				.build();

		ExtraResponse extra = createExtra(extraRequest, organiser.getId(), user.getToken());

		EventResponse eventResponse = addExtraToEvent(organiser.getId(), event.getId(), extra.getId(), user.getToken());
		assert eventResponse.getExtras().size() == 1;
		assert eventResponse.getExtras().get(0).getId().equals(extra.getId());
		assert eventResponse.getExtras().get(0).getName().equals(extra.getName());
		assert eventResponse.getExtras().get(0).getType().equals(extra.getType());
	}
}
