package com.tickets.api.controller.client;

import com.tickets.api.model.LoginRequest;
import com.tickets.api.service.AuthenticationService;
import com.tickets.api.service.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping(LoginController.PATH)
@Slf4j
@Tag(name = "Location API", description = "The Login API allow clients to authenticate.")
public class LoginController {

	public static final String PATH = "v1/login";
	private final AuthenticationService authenticationService;

	@Operation(description = "Login service" )
	@ApiResponse(responseCode = "200", description = "Authenticated")
	@PostMapping()
	public ResponseEntity<LoginResponse> restrictions(@Valid @RequestBody LoginRequest loginRequest) {

		LoginResponse loginResponse = authenticationService.login(loginRequest);
		return ok(loginResponse);
	}


}
