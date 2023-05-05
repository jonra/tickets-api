package com.tickets.api.controller.admin;

import com.tickets.api.auth.Authorization;
import com.tickets.api.enums.Role;
import com.tickets.api.model.CityRequest;
import com.tickets.api.model.CityResponse;
import com.tickets.api.model.CountryRequest;
import com.tickets.api.model.CountryResponse;
import com.tickets.api.service.CountryService;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(CountryController.PATH)
@Slf4j
@Tag(name = "Admin API", description = "The tenant API manages tenant settings.")
public class CountryController {

	public static final String PATH = "v1/countries";
	private final CountryService countryService;

	@Operation(description = "Create a new country.")
	@ApiResponse(responseCode = "200", description = "Country details")
	@PostMapping()
	public ResponseEntity<CountryResponse> createCountry(HttpServletRequest request, @RequestBody CountryRequest countryRequest) {
		Authorization.hasRole(request, Role.TENANT_ADMIN.name());
		CountryResponse country = countryService.createCountry(countryRequest);

		return ResponseEntity.ok(country);
	}

	@Operation(description = "Get all countries.")
	@ApiResponse(responseCode = "200", description = "Country list")
	@GetMapping()
	public ResponseEntity<List<CountryResponse>> getCountries(HttpServletRequest request) {
		List<CountryResponse> countries = countryService.getCountries();

		return ResponseEntity.ok(countries);
	}

	@Operation(description = "Add city to country.")
	@ApiResponse(responseCode = "200", description = "Country details")
	@PutMapping("/{countryId}/cities")
	public ResponseEntity<CountryResponse> addCityToCountry(HttpServletRequest request, @Valid @RequestBody CityRequest cityRequest, @PathVariable String countryId) {
		Authorization.hasRole(request, Role.TENANT_ADMIN.name());

		CountryResponse country = countryService.addCityToCountry(countryId, cityRequest);

		return ResponseEntity.ok(country);
	}

	@Operation(description = "Get cities for country.")
	@ApiResponse(responseCode = "200", description = "Country details")
	@GetMapping("/{countryId}/cities")
	public ResponseEntity<List<CityResponse>> getCities(HttpServletRequest request, @PathVariable("countryId") String countryId) {

		List<CityResponse> country = countryService.getCities(countryId);

		return ResponseEntity.ok(country);
	}
}
