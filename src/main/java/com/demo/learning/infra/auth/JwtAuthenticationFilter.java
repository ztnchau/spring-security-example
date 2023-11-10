package com.demo.learning.infra.auth;

import static com.demo.learning.infra.auth.AuthConstants.TOKEN_HEADER;
import static com.demo.learning.infra.auth.AuthConstants.TOKEN_PREFIX;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final CustomUserDetailsService userDetailsService;

  private final JwtAccessTokenProvider jwtAccessTokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
      FilterChain chain)
      throws ServletException, IOException {
    String header = req.getHeader(TOKEN_HEADER);
    String username = null;
    String authToken = null;
    if (header != null && header.startsWith(TOKEN_PREFIX)) {
      authToken = header.replace(TOKEN_PREFIX, "");
      try {
        username = jwtAccessTokenProvider.getUsernameFromToken(authToken);
      } catch (IllegalArgumentException e) {
        logger.error("TOKEN_INVALID", e);
      } catch (ExpiredJwtException e) {
        logger.warn("TOKEN_EXPIRED", e);
      } catch (SignatureException e) {
        logger.error("Authentication Failed. Username or Password not valid.");
      }
    } else {
      logger.warn("couldn't find bearer string, will ignore the header");
    }
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      UserDetails userDetails = userDetailsService.loadUserByUsername(username);

      if (jwtAccessTokenProvider.validateToken(authToken, userDetails)) {
        UsernamePasswordAuthenticationToken authentication = jwtAccessTokenProvider.getAuthentication(
            userDetails);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        logger.info("authenticated user " + username + ", setting security context");
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }

    chain.doFilter(req, res);
  }
}
