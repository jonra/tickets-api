package com.tickets.api.controller.organiser;

import com.tickets.api.model.TenantResponse;
import com.tickets.api.model.TicketRequest;
import com.tickets.api.model.TicketResponse;
import com.tickets.api.service.TenantService;
import com.tickets.api.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(TicketController.PATH)
@Slf4j
@Tag(name = "Ticket API", description = "Ticket API.")
public class TicketController {

	public static final String PATH = "/v1/organisers/{organiserId}/events/{eventId}/tickets";
	private final TicketService ticketService;
	private final TenantService tenantService;

	@Operation(description = "Create a new ticket.")
	@ApiResponse(responseCode = "200", description = "Ticket details")
	@PostMapping()
	public ResponseEntity<TicketResponse> createTicket(HttpServletRequest request, @RequestBody TicketRequest ticketRequest, @PathVariable String organiserId, @PathVariable String eventId) {
		String attribute = (String) request.getAttribute("clientHost");
		TenantResponse tenantResponse = tenantService.getTenant(attribute);

		TicketResponse ticket = ticketService.createTicket(ticketRequest, organiserId, eventId, tenantResponse.getId());

		return ResponseEntity.ok(ticket);
	}

	@Operation(description = "Add extra to event.")
	@ApiResponse(responseCode = "200", description = "Event details")
	@PutMapping("/{ticketId}/extras/{extraId}")
	public ResponseEntity<TicketResponse> addExtraToOrganiser(HttpServletRequest request, @PathVariable String organiserId, @PathVariable String eventId, @PathVariable String ticketId, @PathVariable String extraId) {
		String attribute = (String) request.getAttribute("clientHost");
		TenantResponse tenantResponse = tenantService.getTenant(attribute);

		TicketResponse eventResponse = ticketService.addExtraToTicket(tenantResponse.getId(), organiserId, eventId, ticketId, extraId);

		return ResponseEntity.ok(eventResponse);
	}


}
