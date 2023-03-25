package com.security.tasks.repositories;

import com.security.tasks.models.entities.CheckTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChecklistRepository extends JpaRepository<CheckTask,Long> {
    List<CheckTask> findAllByTaskIdAndUserId(Long taskId, Long userId);
}
