package com.demo.learning.infra.auth;

import com.demo.learning.persistent.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

  private static final long serialVersionUID = 1L;

  private UUID id;
  private String username;
  @JsonIgnore
  private String password;
  private String firstName;
  private String lastName;

  private Collection<? extends GrantedAuthority> authorities;

  public CustomUserDetails(UUID id, String username, String password, String firstName,
      String lastName,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.authorities = authorities;
  }

  public static CustomUserDetails from(User user) {
    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));
    return new CustomUserDetails(
        user.getId(),
        user.getUsername(),
        user.getPassword(),
        user.getFirstName(),
        user.getLastName(),
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public UUID getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomUserDetails user = (CustomUserDetails) o;
    return Objects.equals(id, user.id);
  }
}
