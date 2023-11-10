package com.demo.learning.persistent.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "work_items")
public class WorkItem {

  @CreatedBy
  protected String createdBy;

  @CreatedDate
  protected Date createdDate;

  @LastModifiedBy
  protected String modifiedBy;

  @LastModifiedDate
  protected Date modifiedDate;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String title;
  private String description;
  private Boolean isCompleted;
  private Date assignedAt;
  private Integer percentage;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
