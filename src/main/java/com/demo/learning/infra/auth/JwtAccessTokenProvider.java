package com.demo.learning.infra.auth;

import static com.demo.learning.infra.auth.AuthConstants.TEMP_SECRET_KEY;
import static com.demo.learning.infra.auth.AuthConstants.TOKEN_HEADER;
import static com.demo.learning.infra.auth.AuthConstants.TOKEN_PREFIX;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAccessTokenProvider {

  // private final AuthProperties authProperties;
  private final JwtParser jwtParser = Jwts.parser().setSigningKey(TEMP_SECRET_KEY);

  public String generateAccessToken(Map<String, Object> claims, Integer expiration) {
    return Jwts.builder()
        .setClaims(claims)
        .setId("123")
        .setIssuer("123")
        .setSubject("123")
        .setAudience("123")
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(Date.from(Instant.now().plus(expiration, ChronoUnit.MINUTES)))
        .signWith(SignatureAlgorithm.HS256, TEMP_SECRET_KEY)
        .compact();
  }

  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(TOKEN_HEADER);
    if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
      return bearerToken.substring(TOKEN_PREFIX.length());
    }
    return null;
  }

  public String getUsernameFromToken(String token) {
    return getAllClaimsFromToken(token).get("username", String.class);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser()
        .setSigningKey(TEMP_SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return (
        username.equals(userDetails.getUsername())
            && !isTokenExpired(token));
  }

  UsernamePasswordAuthenticationToken getAuthentication(final UserDetails userDetails) {

    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }
}
