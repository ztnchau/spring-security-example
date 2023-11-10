package com.demo.learning.application.service;

import com.demo.learning.application.request.SignupRequest;
import com.demo.learning.application.response.LoginResponse;
import com.demo.learning.application.response.SignupResponse;

public interface UserService {

  LoginResponse login(String username, String password);

  SignupResponse signup(SignupRequest signupRequest);

}
