package com.tickets.api.controller.client;

import com.tickets.api.auth.AuthenticationRequest;
import com.tickets.api.auth.AuthenticationResponse;
import com.tickets.api.auth.RegisterRequest;
import com.tickets.api.model.TenantResponse;
import com.tickets.api.service.TenantService;
import com.tickets.api.service.UserAuthenticationService;
import com.tickets.api.service.UserProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(LoginController.PATH)
@Slf4j
@Tag(name = "Login API", description = "The Login API allow clients to authenticate.")
public class LoginController {

	public static final String PATH = "v1/login";
	private final TenantService tenantService;
	private final UserAuthenticationService authenticationService;
	private final UserProfileService userService;


	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(HttpServletRequest httpServletRequest, @RequestBody RegisterRequest request) {
		String attribute = (String) httpServletRequest.getAttribute("clientHost");
		TenantResponse tenantResponse = tenantService.getTenant(attribute);

		AuthenticationResponse register = authenticationService.register(request, tenantResponse.getId());
		return ResponseEntity.ok(register);
	}

	@PostMapping()
	public ResponseEntity<AuthenticationResponse> login(HttpServletRequest httpServletRequest, @RequestBody AuthenticationRequest request) {
		String attribute = (String) httpServletRequest.getAttribute("clientHost");
		TenantResponse tenantResponse = tenantService.getTenant(attribute);

		return ResponseEntity.ok(authenticationService.authenticate(request, tenantResponse.getId()));
	}


}
