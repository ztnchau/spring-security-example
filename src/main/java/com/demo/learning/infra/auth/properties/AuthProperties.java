package com.demo.learning.infra.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.auth.config")
public class AuthProperties {

  private String secretKey;
  private int expiration;  // minute
  private boolean authenticationEnabled;
  private String[] publicEndpoints;
  private boolean disablePermanentToken;
  private String keystorePassword;
  private String jwtTokenId;
  private String jwtTokenIssuer;
  private String jwtTokenSubject;
  private String jwtTokenAudience;
}
