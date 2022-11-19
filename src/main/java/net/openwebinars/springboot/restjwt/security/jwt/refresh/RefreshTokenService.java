package net.openwebinars.springboot.restjwt.security.jwt.refresh;


import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import net.openwebinars.springboot.restjwt.user.model.User;
import net.openwebinars.springboot.restjwt.user.repo.UserRepository;
import net.openwebinars.springboot.restjwt.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    //private final UserService userService;

    @Value("${jwt.refresh.duration}")
    private int durationInMinutes;


    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusSeconds(durationInMinutes * 60));

        refreshToken = refreshTokenRepository.save(refreshToken);


        return refreshToken;
    }

    /*public RefreshToken createRefreshToken(UUID userId) {
        return userService.findById(userId)
                .map(this::createRefreshToken)
                .orElseThrow(() -> new UsernameNotFoundException("Error al crear el token de refresco"));
    }*/

    public RefreshToken verify(RefreshToken refreshToken) {

        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            // Token de refresco caducado. Lo eliminamos y lanzamos excepción
            refreshTokenRepository.delete(refreshToken);
            // Se puede mejorar con excepciones personalizadas
            throw new RuntimeException("Token de refresco expirado: " + refreshToken.getToken() + ". Vuelva a loguearse, por favor.");

        }

        return refreshToken;


    }

    @Transactional
    public int deleteByUser(User user) {
        return refreshTokenRepository.deleteByUser(user);
    }

}
