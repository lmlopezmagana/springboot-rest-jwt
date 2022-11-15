package net.openwebinars.springboot.restjwt.config;

import net.openwebinars.springboot.restjwt.user.model.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return Optional.ofNullable(authentication)
                .map(auth -> (User) auth.getPrincipal())
                .map(User::getId)
                .map(UUID::toString);

    }
}

