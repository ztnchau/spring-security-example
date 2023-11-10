package com.demo.learning.application.response;

import com.demo.learning.application.dto.UserDetailsDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.UUID;
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
public class WorkItemResponse {

  private UUID id;
  private String title;
  private String description;
  private Boolean isCompleted;
  private Date assigned;
  private Integer percentage;
  private UserDetailsDTO owner;
}
