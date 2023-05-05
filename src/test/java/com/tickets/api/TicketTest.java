package com.tickets.api;

import com.tickets.api.auth.AuthenticationResponse;
import com.tickets.api.enums.ConcessionType;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.tickets.api.TestHelper.createEvent;
import static com.tickets.api.TestHelper.createOrganiser;
import static com.tickets.api.TestHelper.createTicket;
import static com.tickets.api.TestHelper.init;
import static com.tickets.api.TestHelper.mergeTicket;

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
					.ticketType(TicketType.DAY)
					.numberOfTickets(100)
					.startTime(LocalDateTime.now())
					.endTime(LocalDateTime.now())
					.concessionType(ConcessionType.STANDARD)
					.price(BigDecimal.TEN)
					.build();
		TicketResponse ticket = createTicket(ticketRequest, organiser.getId(), event.getId(), user.getToken());

		TicketRequest ticketMergeRequest = TicketRequest.builder()
				.name("Updated test ticket")
				.ticketType(TicketType.WEEKEND)
				.numberOfTickets(20)
				.startTime(LocalDateTime.now().plusDays(1))
				.endTime(LocalDateTime.now().plusDays(1))
				.concessionType(ConcessionType.ADULT)
				.price(BigDecimal.ZERO)
				.build();
		TicketResponse updatedTicket = mergeTicket(ticketMergeRequest, organiser.getId(), event.getId(), ticket.getId(), user.getToken());
		assert updatedTicket.getName().equals(ticketMergeRequest.getName());
		assert updatedTicket.getTicketType().equals(ticketMergeRequest.getTicketType());
		assert updatedTicket.getNumberOfTickets().equals(ticketMergeRequest.getNumberOfTickets());
		assert updatedTicket.getStartTime().equals(ticketMergeRequest.getStartTime());
		assert updatedTicket.getEndTime().equals(ticketMergeRequest.getEndTime());
		assert updatedTicket.getConcessionType().equals(ticketMergeRequest.getConcessionType());
		assert updatedTicket.getPrice().intValue() == ticketMergeRequest.getPrice().intValue();

	}

}
