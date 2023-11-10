package com.demo.learning.presentation.api;

import com.demo.learning.application.request.LoginRequest;
import com.demo.learning.application.request.SignupRequest;
import com.demo.learning.application.response.LoginResponse;
import com.demo.learning.application.response.SignupResponse;
import com.demo.learning.application.service.UserService;
import com.demo.learning.application.service.WorkItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final UserService userService;
  private final WorkItemService workItemService;


  @PostMapping("/login")
  public LoginResponse login(
      @RequestBody LoginRequest request) {
    return userService.login(request.getUsername(), request.getPassword());
  }

  @PostMapping("/signup")
  public SignupResponse signup(
      @RequestBody SignupRequest request) {
    return userService.signup(request);
  }

}
