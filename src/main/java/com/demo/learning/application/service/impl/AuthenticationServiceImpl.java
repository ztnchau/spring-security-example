package com.demo.learning.application.service.impl;

import static com.demo.learning.infra.auth.AuthConstants.AUTHORITIES_KEY;
import static com.demo.learning.infra.auth.AuthConstants.ID_KEY;
import static com.demo.learning.infra.auth.AuthConstants.USERNAME_KEY;

import com.demo.learning.application.dto.JwtResponse;
import com.demo.learning.application.service.AuthenticationService;
import com.demo.learning.infra.auth.CustomUserDetails;
import com.demo.learning.infra.auth.JwtAccessTokenProvider;
import io.jsonwebtoken.Claims;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final JwtAccessTokenProvider jwtAccessTokenProvider;
  private final AuthenticationManager authenticationManager;
  // private final AuthProperties authProperties;

  @Override
  public Authentication authenticate(String username, String password) {
    return authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password));
  }

  @Override
  public JwtResponse generateJwtToken(CustomUserDetails userDetails) {
    if (userDetails == null) {
      return null;
    }
    Map<String, Object> claims = new HashMap<>();
    claims.put(ID_KEY, userDetails.getId());
    claims.put(USERNAME_KEY, userDetails.getUsername());
    claims.put(AUTHORITIES_KEY, userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(",")));
    String accessToken = jwtAccessTokenProvider.generateAccessToken(claims,
        30);
    return JwtResponse.builder()
        .accessToken(accessToken)
        .id(jwtAccessTokenProvider.getClaimFromToken(accessToken, Claims::getId))
        .issueAt(jwtAccessTokenProvider.getClaimFromToken(accessToken, Claims::getIssuedAt))
        .expirationAt(jwtAccessTokenProvider.getClaimFromToken(accessToken, Claims::getExpiration))
        .build();
  }
}
