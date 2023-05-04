package com.tickets.api.auth;

import com.tickets.api.model.TenantResponse;
import com.tickets.api.service.TenantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(AuthenticationController.PATH)
public class AuthenticationController {
    public static final String PATH = "v1/auth";
    private final AuthenticationService service;
    private final TenantService tenantService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(HttpServletRequest httpServletRequest, @RequestBody RegisterRequest request) {
        String attribute = (String) httpServletRequest.getAttribute("clientHost");
        TenantResponse tenantResponse = tenantService.getTenant(attribute);

        return ResponseEntity.ok(service.register(request, tenantResponse.getId()));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> register(HttpServletRequest httpServletRequest, @RequestBody AuthenticationRequest request) {
        String attribute = (String) httpServletRequest.getAttribute("clientHost");
        TenantResponse tenantResponse = tenantService.getTenant(attribute);

        return ResponseEntity.ok(service.authenticate(request, tenantResponse.getId()));
    }

}
