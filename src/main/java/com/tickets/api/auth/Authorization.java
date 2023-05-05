package com.tickets.api.auth;

import com.tickets.api.enums.Role;
import com.tickets.api.exceptions.AuthenticationException;
import com.tickets.api.model.UserProfile;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class Authorization {
	private static UserProfile getProfile(HttpServletRequest request) {
		UserProfile profile = Optional.ofNullable((UserProfile) request.getAttribute("profile")).orElseThrow(() -> new AuthenticationException("User profile not found"));
		return profile;
	}

	public static void isSameId(HttpServletRequest request, String userId, String id) {
		UserProfile profile = getProfile(request);
		if (!profile.getUserId().equals(id)) {
			throw new AuthenticationException("You are not authorized to perform this action");
		}
	}
	public static void isOrganiserId(HttpServletRequest request, String organiserId) {
		UserProfile profile = getProfile(request);
		if (!profile.getOrganiserId().equals(organiserId)) {
			throw new AuthenticationException("User is not an organiser for " + organiserId);
		}
	}
	public static String getTenantId(HttpServletRequest request) {
		UserProfile profile = Optional.ofNullable(getProfile(request)).orElseThrow(() -> new AuthenticationException("Tenant not found"));
		return profile.getTenantId();
	}

	public static String getUserId(HttpServletRequest request) {
		UserProfile profile = Optional.ofNullable(getProfile(request)).orElseThrow(() -> new AuthenticationException("User not found"));
		return profile.getUserId();
	}

	public static void hasRole(HttpServletRequest request, Role role) {
		UserProfile profile = getProfile(request);
		Optional.ofNullable(profile.getRoles()).orElseThrow(() -> new AuthenticationException("User does not have role " + role));
		profile.getRoles().stream().filter(r -> r.equals(role)).findFirst().orElseThrow(() -> new AuthenticationException("User does not have role " + role));

	}
}
