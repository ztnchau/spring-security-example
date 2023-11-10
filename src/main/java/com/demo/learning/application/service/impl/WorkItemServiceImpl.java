package com.demo.learning.application.service.impl;

import com.demo.learning.application.dto.UserDetailsDTO;
import com.demo.learning.application.request.WorkItemRequest;
import com.demo.learning.application.response.WorkItemResponse;
import com.demo.learning.application.service.WorkItemService;
import com.demo.learning.persistent.entities.User;
import com.demo.learning.persistent.entities.WorkItem;
import com.demo.learning.persistent.repositories.UserRepository;
import com.demo.learning.persistent.repositories.WorkItemRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkItemServiceImpl implements WorkItemService {

  private final WorkItemRepository workItemRepository;
  private final UserRepository userRepository;

  @Override
  public List<WorkItemResponse> findByUserId(UUID userId) {
    return workItemRepository.findByUserId(userId)
        .stream().map(this::toWorkItemResponse).collect(Collectors.toList());
  }

  @Override
  public List<WorkItemResponse> findAll() {
    return workItemRepository.findAll().stream()
        .map(this::toWorkItemResponse)
        .collect(Collectors.toList());
  }

  @Override
  public List<WorkItemResponse> createWorkItems(UUID userId, WorkItemRequest request) {
    List<WorkItem> entities = toEntities(request,
        userRepository.findById(userId).orElse(new User()));
    return workItemRepository.saveAll(entities).stream()
        .map(this::toWorkItemResponse).collect(Collectors.toList());
  }

  private List<WorkItem> toEntities(WorkItemRequest request, User user) {
    return request.getItems().stream().map(dto -> {
      User owner = user;
      Optional<User> assigned = userRepository.findById(dto.getAssignTo());
      if (assigned.isPresent()) {
        owner = assigned.get();
      }
     return WorkItem.builder()
         .title(dto.getTitle())
         .description(dto.getDescription())
         .isCompleted(Boolean.FALSE)
         .assignedAt(new Date())
         .percentage(0)
         .user(owner)
         .createdBy(user.getUsername())
         .createdDate(new Date())
         .build();
    }).collect(Collectors.toList());
  }

  private WorkItemResponse toWorkItemResponse(WorkItem en) {
    return WorkItemResponse.builder()
        .id(en.getId())
        .title(en.getTitle())
        .description(en.getDescription())
        .assigned(en.getAssignedAt())
        .isCompleted(en.getIsCompleted())
        .percentage(en.getPercentage())
        .owner(getOwner(en.getUser()))
        .build();
  }

  private UserDetailsDTO getOwner(User user) {
    if (user == null) {
      return null;
    }
    return UserDetailsDTO.builder()
        .username(user.getUsername())
        .role(user.getRole())
        .build();
  }
}
