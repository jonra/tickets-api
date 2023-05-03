package com.tickets.api.controller.admin;

import com.tickets.api.model.TenantRequest;
import com.tickets.api.model.TenantResponse;
import com.tickets.api.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping(TenantController.PATH)
@Slf4j
@Tag(name = "Admin API", description = "The tenant API manages tenant settings.")
public class TenantController {

	public static final String PATH = "v1/tenants";
	private final TenantService tenantService;

	@Operation(description = "Create a new tenant.")
	@ApiResponse(responseCode = "200", description = "Tenant details")
	@PostMapping()
	public ResponseEntity<TenantResponse> createTenant(HttpServletRequest request, @RequestBody TenantRequest tenantRequest) {

		TenantResponse tenant = tenantService.createTenant(tenantRequest);

		return ResponseEntity.ok(tenant);
	}
}