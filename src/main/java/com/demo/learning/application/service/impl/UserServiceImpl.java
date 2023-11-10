package com.demo.learning.application.service.impl;

import com.demo.learning.application.dto.JwtResponse;
import com.demo.learning.application.exception.BadRequestException;
import com.demo.learning.application.request.SignupRequest;
import com.demo.learning.application.response.LoginResponse;
import com.demo.learning.application.response.SignupResponse;
import com.demo.learning.application.service.AuthenticationService;
import com.demo.learning.application.service.UserService;
import com.demo.learning.infra.auth.CustomUserDetails;
import com.demo.learning.persistent.entities.User;
import com.demo.learning.persistent.enums.Role;
import com.demo.learning.persistent.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final AuthenticationService authenticationService;

  @Override
  public LoginResponse login(String username, String password) {
    Authentication authentication = authenticationService.authenticate(username, password);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    JwtResponse jwtResponse = authenticationService.generateJwtToken(userDetails);
    return LoginResponse.builder()
        .accessToken(jwtResponse.getAccessToken())
        .id(jwtResponse.getId())
        .issueAt(jwtResponse.getIssueAt())
        .expirationAt(jwtResponse.getExpirationAt())
        .build();
  }

  @Override
  public SignupResponse signup(SignupRequest signupRequest) {
    final User existingUser = userRepository.findByUsername(signupRequest.getUsername());
    if (existingUser != null) {
      throw new BadRequestException("USERNAME_EXISTED", "Username is already used");
    }
    User newUser = new User();
    newUser.setUsername(signupRequest.getUsername());
    newUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
    newUser.setFirstName(signupRequest.getFirstName());
    newUser.setLastName(signupRequest.getLastName());
    newUser.setRole(signupRequest.getRole() != null ? signupRequest.getRole() : Role.ROLE_USER);

    userRepository.save(newUser);
    return SignupResponse.builder().result("User registered successfully!").build();
  }
}
