package by.zemich.userservice.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String jwtSecret = "yourSecretKey";
    private final long jwtExpirationInMs = 3600000;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        return Jwts.builder().subject(username).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + jwtExpirationInMs)).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }
}
