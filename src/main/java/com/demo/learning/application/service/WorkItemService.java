package com.demo.learning.application.service;

import com.demo.learning.application.request.WorkItemRequest;
import com.demo.learning.application.response.WorkItemResponse;
import java.util.List;
import java.util.UUID;

public interface WorkItemService {

  List<WorkItemResponse> findByUserId(UUID userId);

  List<WorkItemResponse> findAll();

  List<WorkItemResponse> createWorkItems(UUID userId, WorkItemRequest request);

}
