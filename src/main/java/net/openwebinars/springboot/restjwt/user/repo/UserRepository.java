package net.openwebinars.springboot.restjwt.user.repo;

import net.openwebinars.springboot.restjwt.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findFirstByUsername(String username);

}
