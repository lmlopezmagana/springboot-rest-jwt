package net.openwebinars.springboot.restjwt;

import net.openwebinars.springboot.restjwt.user.dto.CreateUserRequest;
import net.openwebinars.springboot.restjwt.user.model.User;
import net.openwebinars.springboot.restjwt.user.model.UserRole;
import net.openwebinars.springboot.restjwt.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
class RestJwtApplicationTests {

	@Autowired
	UserService userService;

	@Test
	void registerNewUser() {

		CreateUserRequest newUser = CreateUserRequest.builder()
				.fullName("Pepe Pérez")
				.username("pepe.perez")
				.password("12345678")
				.build();
		User saved = userService.createUserWithUserRole(newUser);

		Assertions.assertEquals(newUser.getUsername(), saved.getUsername());
		Assertions.assertNotNull(saved.getId());

	}
}
