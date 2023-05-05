package com.tickets.api.controller.organiser;

import com.tickets.api.auth.Authorization;
import com.tickets.api.model.OrganiserRequest;
import com.tickets.api.model.OrganiserResponse;
import com.tickets.api.model.UserResponse;
import com.tickets.api.service.OrganiserService;
import com.tickets.api.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(OrganiserController.PATH)
@Slf4j
@Tag(name = "Organiser API", description = "The organiser API for managing organisers.")
public class OrganiserController {

	public static final String PATH = "v1/organisers";
	private final TenantService tenantService;
	private final OrganiserService organiserService;

	@Operation(description = "Create a new organiser.")
	@ApiResponse(responseCode = "200", description = "Event details")
	@PostMapping()
	public ResponseEntity<OrganiserResponse> createOrganiser(HttpServletRequest request, @RequestBody OrganiserRequest organiserUserRequest) {
		String tenantId = Authorization.getTenantId(request);
		String userId = Authorization.getUserId(request);

		OrganiserResponse organiser = organiserService.createOrganiser(organiserUserRequest, tenantId);
		// Add current user to organiser
		organiserService.addUserToOrganiser(organiser.getId(), userId, tenantId);
		return ResponseEntity.ok(organiserService.getOrganiser(organiser.getId(), tenantId));
	}

	@Operation(description = "Get an organiser.")
	@ApiResponse(responseCode = "200", description = "Organiser details")
	@GetMapping("/{organiserId}")
	public ResponseEntity<OrganiserResponse> getOrganiser(HttpServletRequest request, @PathVariable String organiserId) {
		Authorization.isOrganiserId(request, organiserId);
		String tenantId = Authorization.getTenantId(request);
		String userId = Authorization.getUserId(request);

		OrganiserResponse organiser = organiserService.getOrganiser(organiserId, tenantId);
		return ResponseEntity.ok(organiser);
	}

	@Operation(description = "Add user to organiser.")
	@ApiResponse(responseCode = "200", description = "Event details")
	@PutMapping("/{organiserId}/users/{userId}")
	public ResponseEntity<OrganiserResponse> addUserToOrganiser(HttpServletRequest request, @PathVariable String organiserId, @PathVariable String userId) {
		Authorization.isOrganiserId(request, organiserId);
		String tenantId = Authorization.getTenantId(request);

		OrganiserResponse organiserResponse = organiserService.addUserToOrganiser(organiserId, userId, tenantId);

		return ResponseEntity.ok(organiserResponse);
	}

	@Operation(description = "Get users for organiser.")
	@ApiResponse(responseCode = "200", description = "Event details")
	@GetMapping("/{organiserId}/users")
	public ResponseEntity<List<UserResponse>> getUsers(HttpServletRequest request, @PathVariable String organiserId) {
		Authorization.isOrganiserId(request, organiserId);
		String tenantId = Authorization.getTenantId(request);

		List<UserResponse> users = organiserService.getUsersForOrganiser(organiserId, tenantId);

		return ResponseEntity.ok(users);
	}



}
