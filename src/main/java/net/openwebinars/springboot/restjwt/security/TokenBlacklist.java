package net.openwebinars.springboot.restjwt.security;

import net.openwebinars.springboot.restjwt.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBlacklist extends JpaRepository<BlackList, String> {
}
