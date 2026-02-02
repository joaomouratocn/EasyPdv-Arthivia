package br.com.arthivia.api.infra.security;

import br.com.arthivia.api.model.entitys.UserEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Authentication authentication){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var userEntity = (UserEntity) authentication.getPrincipal();
            assert userEntity != null;
            return JWT.create()
                    .withIssuer("bck-easyPDV")
                    .withSubject(userEntity.getUsername())
                    .withClaim("id", userEntity.getUserId())
                    .withClaim("role", userEntity.getUserRole().name())
                    .withExpiresAt(generateExpDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token");
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("bck-easyPDV")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }

    private Instant generateExpDate() {
        return LocalDateTime.now().plusMinutes(10L).toInstant(ZoneOffset.ofHours(-3));
    }
}
