package com.productivity.goal.repositories;

import com.productivity.goal.models.entities.Goal;
import com.productivity.goal.models.enums.GoalCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    Page<Goal> findByUserId(Long userId, Pageable pageable);

    Optional<Goal> findByIdAndUserId(Long id, Long userId);

    Page<Goal> findByUserIdAndCategory(Long userId, GoalCategory category, Pageable pageable);

    boolean existsByTitleAndUser_Id(String title, Long userId);
}



