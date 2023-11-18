package net.openwebinars.springboot.restjwt.security.jwt.access;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.java.Log;
import net.openwebinars.springboot.restjwt.security.errorhandling.JwtTokenException;
import net.openwebinars.springboot.restjwt.user.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;



import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Log
@Service
public class JwtProvider {

    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.duration}")
    private int jwtLifeInDays;
    //private int jwtLifeInMinutes;

    private JwtParser jwtParser;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {

        secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        //jwtParser = Jwts.parserBuilder()
        jwtParser = Jwts.parser()
                //.setSigningKey(secretKey)
                .verifyWith(secretKey)
                .build();
    }


    public String generateToken(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return generateToken(user);

    }

    public String generateToken(User user) {
        Date tokenExpirationDateTime =
                Date.from(
                        LocalDateTime
                                .now()
                                .plusDays(jwtLifeInDays)
                                //.plusMinutes(jwtLifeInMinutes)
                                .atZone(ZoneId.systemDefault())
                                .toInstant()
                );

        return Jwts.builder()
                .header().type(TOKEN_TYPE)
                .and()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(tokenExpirationDateTime)
                .signWith(secretKey)
                .compact();
                /*.setHeaderParam("typ", TOKEN_TYPE)
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(tokenExpirationDateTime)
                .signWith(secretKey)
                .compact();*/

    }


    public UUID getUserIdFromJwtToken(String token) {



        return UUID.fromString(
                //jwtParser.parseClaimsJws(token).getBody().getSubject()
                jwtParser.parseSignedClaims(token).getPayload().getSubject()
        );
    }


    public boolean validateToken(String token) {

        try {
            //jwtParser.parseClaimsJws(token);
            jwtParser.parse(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            log.info("Error con el token: " + ex.getMessage());
            throw new JwtTokenException(ex.getMessage());
        }
        //return false;

    }



}
