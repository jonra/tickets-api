package com.tickets.api;

import com.tickets.api.auth.AuthenticationResponse;
import com.tickets.api.enums.Role;
import com.tickets.api.model.UserResponse;
import com.tickets.api.model.UserRoleRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.tickets.api.TestHelper.addRoleToUser;
import static com.tickets.api.TestHelper.init;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = ApiApplication.class)
	class UserTest extends RestAssuredBaseTestClass {


	@Test
	void create_user() {
		AuthenticationResponse user = init();

		assert user.getEmail().equals(user.getEmail());
		assert user.getFirstName().equals(user.getFirstName());
		assert user.getLastName().equals(user.getLastName());
		assert user.getId() != null;
	}

	@Test
	void add_role_to_user() {
		AuthenticationResponse user = init();
		UserResponse userResponse = addRoleToUser(UserRoleRequest.builder().role(Role.ORGANISER).build(), user.getId(), user.getToken());

		assert userResponse.getRoles().size() == 3;
	}

}
