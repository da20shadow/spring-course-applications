package com.security.tasks.repositories;

import com.security.tasks.models.entities.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistRepository extends JpaRepository<ChecklistItem,Long> {
    List<ChecklistItem> findAllByTaskIdAndUserId(Long taskId, Long userId);

    Optional<ChecklistItem> findByTitleAndTaskIdAndUserId(String title, Long taskId, Long userId);

    Optional<ChecklistItem> findByIdAndUserId(Long itemId, Long userId);
}
