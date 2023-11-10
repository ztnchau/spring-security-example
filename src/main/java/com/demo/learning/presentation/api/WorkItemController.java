package com.demo.learning.presentation.api;

import com.demo.learning.application.request.WorkItemRequest;
import com.demo.learning.application.response.WorkItemResponse;
import com.demo.learning.application.service.UserService;
import com.demo.learning.application.service.WorkItemService;
import com.demo.learning.infra.auth.CustomUserDetails;
import com.demo.learning.persistent.enums.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class WorkItemController {

  private final WorkItemService workItemService;
  private final UserService userService;

  @GetMapping("/workItems")
  public List<WorkItemResponse> listWorkItems(
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    if (userDetails != null) {
      Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
      if (authorities.stream()
          .anyMatch(auth -> Role.ROLE_ADMIN.name().equals(auth.getAuthority()))) {
        return workItemService.findAll();
      } else if (authorities.stream()
          .anyMatch(auth -> Role.ROLE_USER.name().equals(auth.getAuthority()))) {
        return workItemService.findByUserId(userDetails.getId());
      }
    }
    return new ArrayList<>();
  }

  @PostMapping("/workItems")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public List<WorkItemResponse> createWorkItems(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody WorkItemRequest request) {
    if (userDetails != null) {
      return workItemService.createWorkItems(userDetails.getId(), request);
    }
    return new ArrayList<>();
  }
}
