package com.tickets.api;

import com.tickets.api.enums.Role;
import com.tickets.api.model.TenantRequest;
import com.tickets.api.model.UserRequest;
import com.tickets.api.model.UserResponse;
import com.tickets.api.model.UserRoleRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.tickets.api.TestHelper.addRoleToUser;
import static com.tickets.api.TestHelper.createTenant;
import static com.tickets.api.TestHelper.createUser;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = ApiApplication.class)
	class UserTest extends RestAssuredBaseTestClass {


	@Test
	void create_user() {
		createTenant(TenantRequest.builder().host("127.0.0.1").name("local").build());

		UserRequest userRequest = UserRequest.builder()
				.email("jon@test.com")
				.password("123456")
				.firstName("Jon")
				.lastName("Doe")
				.build();

		UserResponse user = createUser(userRequest);
		assert user.getEmail().equals(userRequest.getEmail());
		assert user.getFirstName().equals(userRequest.getFirstName());
		assert user.getLastName().equals(userRequest.getLastName());
		assert user.getId() != null;
	}

	@Test
	void add_role_to_user() {
		createTenant(TenantRequest.builder().host("127.0.0.1").name("local").build());

		UserRequest userRequest = UserRequest.builder()
				.email("jon@test.com")
				.password("123456")
				.firstName("Jon")
				.lastName("Doe")
				.build();

		UserResponse user = createUser(userRequest);
		UserResponse userResponse = addRoleToUser(UserRoleRequest.builder().role(Role.ORGANISER).build(), user.getId());

		assert userResponse.getRoles().size() == 1;
	}

}
