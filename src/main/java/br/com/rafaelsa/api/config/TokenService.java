package br.com.rafaelsa.api.config;

import br.com.rafaelsa.api.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secretKey;

  @Value("${api.security.token.expiration}")
  private long JWT_EXPIRATION;

  public String generateToken(User user) {
    try {

      Date now = new Date();
      Date expirationDate = new Date(now.getTime() + JWT_EXPIRATION);

       Algorithm algorithm = Algorithm.HMAC256(secretKey);
       String token = JWT.create()
           .withIssuer("rocketnotes")
           .withIssuedAt(now)
           .withSubject(String.valueOf(user.getId()))
           .withExpiresAt(expirationDate)
           .sign(algorithm);

       return token;

    } catch (JWTCreationException ex) {
      throw new RuntimeException("Error while generating token - ", ex);
    }
  }

  public DecodedJWT validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secretKey);
      return JWT.require(algorithm)
          .withIssuer("rocketnotes")
          .build()
          .verify(token);
    } catch (JWTVerificationException e) {
      e.printStackTrace();
      return null;
    }
  }

  public long getExpirationTime() {
    return JWT_EXPIRATION;
  }
}
