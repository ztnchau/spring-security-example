package com.demo.learning.persistent.repositories;

import com.demo.learning.persistent.entities.WorkItem;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkItemRepository extends JpaRepository<WorkItem, UUID> {

  List<WorkItem> findByUserId(UUID userId);

}
