package br.com.rafaelsa.api.servicies;

import br.com.rafaelsa.api.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

  @Value("${api.security.token.secret}")
  private String secret;

  @Value("${security.jwt.expiration-time}")
  private long jwtExpiration;

  public long getExpirationTime() {
    return this.jwtExpiration;
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(User user) {
    return generateToken(new HashMap<>(), user);
  }

  public String generateToken(Map<String, Object> extraClaims, User user) {
    return buildToken(extraClaims, user, generateExpirationDate(jwtExpiration));
  }

  private String buildToken(Map<String, Object> extraClaims, User user, Date expiration) {
    return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(Long.toString(user.getId()))
        .setIssuedAt(creationDate())
        .setExpiration(expiration)
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isTokenValid(String token, UserDetails user) {
    final String username = extractUsername(token);
    return (username.equals(user.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Date creationDate() {
    return new Date(System.currentTimeMillis());
  }

  private Date generateExpirationDate(long expiration) {
    return new Date(System.currentTimeMillis() + expiration);
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
