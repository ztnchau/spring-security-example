package com.demo.learning.application.service;

import com.demo.learning.application.dto.JwtResponse;
import com.demo.learning.infra.auth.CustomUserDetails;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

  Authentication authenticate(String username, String password);

  JwtResponse generateJwtToken(CustomUserDetails userDetails);

}
