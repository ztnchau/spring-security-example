package com.demo.learning.application.request;

import com.demo.learning.persistent.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignupRequest {

  @NotNull
  private String username;
  @NotNull
  private String password;
  private String firstName;
  private String lastName;
  private Role role;
}
