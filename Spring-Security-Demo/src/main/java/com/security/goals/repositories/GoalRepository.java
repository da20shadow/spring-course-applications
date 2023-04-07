package com.security.goals.repositories;

import com.security.goals.models.dtos.GoalDTO;
import com.security.goals.models.entities.Goal;
import com.security.goals.models.enums.GoalCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal,Long> {
    boolean existsByTitleAndUser_Id(String title, Long userId);

    Page<Goal> findByUserId(Long userId, Pageable pageable);

    Optional<Goal> findByIdAndUserId(Long goalId, Long userId);
    Page<Goal> findByUserIdAndCategory(Long user_id, GoalCategory category, Pageable pageable);

    boolean existsByIdAndUser_Id(Long goalId, Long id);
}