package com.tickets.api.controller.organiser;

import com.tickets.api.auth.Authorization;
import com.tickets.api.model.ExtraRequest;
import com.tickets.api.model.ExtraResponse;
import com.tickets.api.service.ExtraService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ExtraController.PATH)
@Slf4j
@Tag(name = "Extra API", description = "Extra API.")
public class ExtraController {

	public static final String PATH = "v1/";
	private final TenantService tenantService;
	private final ExtraService extraService;

	@Operation(description = "Create a new extra.")
	@ApiResponse(responseCode = "200", description = "Event details")
	@PostMapping("organisers/{organiserId}/extras")
	public ResponseEntity<ExtraResponse> createExtra(HttpServletRequest request, @RequestBody ExtraRequest extraRequest, @PathVariable String organiserId) {
		Authorization.isOrganiserId(request, organiserId);
		String tenantId = Authorization.getTenantId(request);

		ExtraResponse extra = extraService.createExtra(extraRequest, organiserId, tenantId);

		return ResponseEntity.ok(extra);
	}

	@Operation(description = "Get extras for organiser.")
	@ApiResponse(responseCode = "200", description = "Extra details")
	@GetMapping("organisers/{organiserId}/extras")
	public ResponseEntity<List<ExtraResponse>> getExtrasForOrganiser(HttpServletRequest request, @PathVariable String organiserId) {
		Authorization.isOrganiserId(request, organiserId);
		String tenantId = Authorization.getTenantId(request);



		List<ExtraResponse> extra = extraService.getExtras(organiserId, tenantId);

		return ResponseEntity.ok(extra);
	}

}
