package com.tickets.api.service;

import com.tickets.api.enums.Role;
import com.tickets.api.exceptions.AuthenticationException;
import com.tickets.api.model.LoginRequest;
import com.tickets.api.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AuthenticationService {
	public LoginResponse login(LoginRequest loginRequest) {
		List<User> users = new ArrayList<>();
		users.add(User.builder().email("jon@townmaking.com").password("Townmaking@1000").firstName("Jon").lastName("Rasmussen").tenant("TENANT").build());
		users.add(User.builder().email("demo@townmaking.com").password("Townmaking@1000").firstName("Demo").lastName("Demo").tenant("TENANT").build());
		users.add(User.builder().email("user@townmaking.com").password("Townmaking@1000").firstName("User").lastName("User").tenant("TENANT").build());

		users.add(User.builder().email("ams@townmaking.com").password("Townmaking@1000").firstName("Ams").lastName("Tenant").tenant("AMS").build());

		users.add(User.builder().email("aruna@townmaking.com").password("Townmaking@1000").firstName("Aruna").lastName("User").tenant("ARUNA").build());

		Map<String, List<Role>> roles = Map.of(
				"jon@townmaking.com", List.of(Role.ADMIN),
				"aruna@townmaking.com", List.of(Role.ADMIN),
				"demo@townmaking.com", List.of(Role.ADMIN)
		);
		return users.stream()
				.filter(user -> user.getEmail().equals(loginRequest.getEmail()) && user.getPassword().equals(loginRequest.getPassword()))
				.map(user -> LoginResponse.builder()
								.email(user.getEmail())
								.firstName(user.getFirstName())
								.lastName(user.getLastName())
								.tenant(user.getTenant())
								.accessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
								.expiresIn(360000)
								.tokenType("Bearer")
								.roles(roles.get(user.getEmail()))
								.build())
				.findFirst()
				.orElseThrow(() -> new AuthenticationException("Invalid email or password"));

	}
}
