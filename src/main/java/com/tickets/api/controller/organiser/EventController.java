package com.tickets.api.controller.organiser;

import com.tickets.api.auth.Authorization;
import com.tickets.api.model.EventRequest;
import com.tickets.api.model.EventResponse;
import com.tickets.api.service.EventService;
import com.tickets.api.service.TenantService;
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
@RequestMapping(EventController.PATH)
@Slf4j
@Tag(name = "Event API", description = "Event API")
public class EventController {

	public static final String PATH = "v1/organisers/{organiserId}/events";
	private final EventService eventService;
	private final TenantService tenantService;

	@Operation(description = "Create a new event.")
	@ApiResponse(responseCode = "200", description = "Event details")
	@PostMapping()
	public ResponseEntity<EventResponse> createEvent(HttpServletRequest request, @RequestBody EventRequest eventRequest, @PathVariable String organiserId) {
		Authorization.isOrganiserId(request, organiserId);
		String tenantId = Authorization.getTenantId(request);

		EventResponse event = eventService.createEvent(eventRequest, organiserId, tenantId);

		return ResponseEntity.ok(event);
	}

	@Operation(description = "Add extra to event.")
	@ApiResponse(responseCode = "200", description = "Event details")
	@PutMapping("/{eventId}/extras/{extraId}")
	public ResponseEntity<EventResponse> addExtraToOrganiser(HttpServletRequest request, @PathVariable String organiserId, @PathVariable String eventId, @PathVariable String extraId) {
		Authorization.isOrganiserId(request, organiserId);
		String tenantId = Authorization.getTenantId(request);

		EventResponse eventResponse = eventService.addExtraToEvent(organiserId, eventId, extraId, tenantId);

		return ResponseEntity.ok(eventResponse);
	}


}
