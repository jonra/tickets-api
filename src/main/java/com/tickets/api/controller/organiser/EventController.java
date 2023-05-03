package com.tickets.api.controller.organiser;

import com.tickets.api.model.EventRequest;
import com.tickets.api.model.EventResponse;
import com.tickets.api.model.ExtraRequest;
import com.tickets.api.model.TenantResponse;
import com.tickets.api.service.EventService;
import com.tickets.api.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
@RequestMapping(EventController.PATH)
@Slf4j
@Tag(name = "Zone API", description = "The Zone API allows the location administrators add restrictions and define which assets the restrictions are for.")
public class EventController {

	public static final String PATH = "v1";
	private final EventService eventService;
	private final TenantService tenantService;

	@Operation(description = "Create a new event.")
	@ApiResponse(responseCode = "200", description = "Event details")
	@PostMapping("/organisers/{organiserId}/events")
	public ResponseEntity<EventResponse> createEvent(HttpServletRequest request, @RequestBody EventRequest eventRequest, @PathVariable String organiserId) {
		String attribute = (String) request.getAttribute("clientHost");
		TenantResponse tenantResponse = tenantService.getTenant(attribute);

		EventResponse event = eventService.createEvent(eventRequest, organiserId, tenantResponse.getId());

		return ResponseEntity.ok(event);
	}

	@Operation(description = "Add extra to event.")
	@ApiResponse(responseCode = "200", description = "Event details")
	@PutMapping("/{eventId}/extras")
	public ResponseEntity<EventResponse> addExtraToOrganiser(HttpServletRequest request, @Valid @RequestBody ExtraRequest extraRequest, @PathVariable String eventId) {
		String attribute = (String) request.getAttribute("clientHost");
		TenantResponse tenantResponse = tenantService.getTenant(attribute);

		EventResponse eventResponse = eventService.addExtraToEvent(extraRequest, eventId, tenantResponse.getId());

		return ResponseEntity.ok(eventResponse);
	}


}
