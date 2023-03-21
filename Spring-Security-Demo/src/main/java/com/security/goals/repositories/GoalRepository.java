package com.security.goals.repositories;

import com.security.goals.models.entities.Goal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal,Long> {
    boolean existsByTitleAndUser_Id(String title, Long userId);

    Page<Goal> findByUserId(Long userId, Pageable pageable);

    Optional<Goal> findByIdAndUserId(Long goalId, Long userId);
}