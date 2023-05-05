package com.tickets.api.config.security;

import com.tickets.api.enums.Role;
import com.tickets.api.model.OrganiserResponse;
import com.tickets.api.model.TenantResponse;
import com.tickets.api.model.UserProfile;
import com.tickets.api.model.UserResponse;
import com.tickets.api.service.TenantService;
import com.tickets.api.service.UserProfileService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired TenantService tenantService;
    @Autowired UserProfileService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                String clientHost = request.getRemoteHost();
                TenantResponse tenantResponse = tenantService.getTenant(clientHost);
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                List<Role> jwtRoles = jwtService.extractRoles(jwt);
                String jwtEmail = jwtService.extractEmail(jwt);
                String jwtUserId = jwtService.extractUserId(jwt);
                String jwtTenantId = jwtService.extractTenantId(jwt);
                String jwtIssuer = jwtService.extractIssuer(jwt);

                UserResponse user = userService.getUser(jwtUserId, jwtTenantId);
                OrganiserResponse organiser = user.getOrganiser();

                assert tenantResponse.equals(jwtTenantId);
                assert jwtEmail.equals(userEmail);
                assert jwtIssuer.equals(tenantResponse.getIssuer());

                UserProfile profile = UserProfile.builder()
                        .email(jwtEmail)
                        .userId(jwtUserId)
                        .roles(jwtRoles)
                        .tenantId(jwtTenantId)
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .organiserId(Optional.ofNullable(organiser).map(o -> o.getId()).orElse(null))
                        .build();

                request.setAttribute("profile", profile);

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
