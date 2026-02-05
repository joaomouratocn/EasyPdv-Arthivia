package br.com.arthivia.api.infra.security;

import br.com.arthivia.api.model.entitys.UserEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserEntity userEntity, boolean refresh) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            assert userEntity != null;
            return JWT.create()
                    .withIssuer("bck-easyPDV")
                    .withSubject(userEntity.getUsername())
                    .withClaim("username", userEntity.getUsername())
                    .withClaim("role", userEntity.getUserRole().name())
                    .withExpiresAt(generateExpDate(refresh))
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
            throw new JWTVerificationException(e.getMessage());
        }
    }


    private Instant generateExpDate(boolean isRefresh) {
        if (isRefresh) {
            return LocalDateTime.now().plusMinutes(3).toInstant(ZoneOffset.ofHours(-3));
        } else {
            return LocalDateTime.now().plusMinutes(1).toInstant(ZoneOffset.ofHours(-3));
        }
    }
}
