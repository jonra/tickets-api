package com.tickets.api.controller.client;

import com.tickets.api.model.TenantResponse;
import com.tickets.api.model.UserRequest;
import com.tickets.api.model.UserResponse;
import com.tickets.api.model.UserRoleRequest;
import com.tickets.api.service.TenantService;
import com.tickets.api.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping(UserController.PATH)
@Slf4j
@Tag(name = "User API", description = "The Login API allow clients to authenticate.")
public class UserController {

	public static final String PATH = "v1/users";
	private final UserProfileService userService;
	private final TenantService tenantService;

	@Operation(description = "Create user" )
	@ApiResponse(responseCode = "200", description = "Authenticated")
	@PostMapping()
	public ResponseEntity<UserResponse> createUser(HttpServletRequest request, @Valid @RequestBody UserRequest userRequest) {
		String attribute = (String) request.getAttribute("clientHost");
		TenantResponse tenantResponse = tenantService.getTenant(attribute);

		UserResponse user = userService.createUser(userRequest, tenantResponse.getId());
		return ok(user);
	}

	@Operation(description = "Get user" )
	@ApiResponse(responseCode = "200", description = "Authenticated")
	@GetMapping("/{userId}")
	public ResponseEntity<UserResponse> getUser(HttpServletRequest request, @PathVariable String userId) {
		String attribute = (String) request.getAttribute("clientHost");
		TenantResponse tenantResponse = tenantService.getTenant(attribute);

		UserResponse user = userService.getUser(userId, tenantResponse.getId());
		return ok(user);
	}

	@Operation(description = "Add user roles" )
	@ApiResponse(responseCode = "200", description = "Authenticated")
	@PutMapping("/{userId}/roles")
	public ResponseEntity<UserResponse> addRolesToUser(HttpServletRequest request, @Valid @RequestBody UserRoleRequest userRequest, @PathVariable String userId) {
		String attribute = (String) request.getAttribute("clientHost");
		TenantResponse tenantResponse = tenantService.getTenant(attribute);

		UserResponse user = userService.addRoleToUser(userRequest, userId, tenantResponse.getId());
		return ok(user);
	}


}
