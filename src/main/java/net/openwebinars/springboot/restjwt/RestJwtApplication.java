package net.openwebinars.springboot.restjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RestJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestJwtApplication.class, args);
	}

}
