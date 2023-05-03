package com.tickets.api.controller.organiser;

import com.tickets.api.model.OrganiserRequest;
import com.tickets.api.model.OrganiserResponse;
import com.tickets.api.model.OrganiserUserRequest;
import com.tickets.api.model.TenantResponse;
import com.tickets.api.service.OrganiserService;
import com.tickets.api.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		String attribute = (String) request.getAttribute("clientHost");
		TenantResponse tenantResponse = tenantService.getTenant(attribute);

		OrganiserResponse organiser = organiserService.createOrganiser(organiserUserRequest, tenantResponse.getId());
		// Add current user to organiser
//		organiserService.addUserToOrganiser(OrganiserUserRequest.builder().userId("").organiserId(organiser.getId()).build(), tenantResponse.getId());
		return ResponseEntity.ok(organiser);
	}

	@Operation(description = "Add user to organiser.")
	@ApiResponse(responseCode = "200", description = "Event details")
	@PutMapping("/users")
	public ResponseEntity<OrganiserResponse> addUserToOrganiser(HttpServletRequest request, @Valid @RequestBody OrganiserUserRequest userRequest) {
		String attribute = (String) request.getAttribute("clientHost");
		TenantResponse tenantResponse = tenantService.getTenant(attribute);

		OrganiserResponse organiserResponse = organiserService.addUserToOrganiser(userRequest, tenantResponse.getId());

		System.out.println("organiserResponse = " + organiserResponse.getUsers());
		return ResponseEntity.ok(organiserResponse);
	}



}
