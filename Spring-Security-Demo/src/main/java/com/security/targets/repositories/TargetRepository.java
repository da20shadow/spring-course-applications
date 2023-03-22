package com.security.targets.repositories;

import com.security.targets.models.entities.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TargetRepository extends JpaRepository<Target,Long> {
    Optional<Target> findByIdAndUserId(Long targetId, Long userId);

    Set<Target> findAllByGoalIdAndUserId(Long goalId, Long userId);

    @Query("SELECT COUNT(t) FROM Target t WHERE t.goal.id = :goalId")
    int countTargetsByGoalId(Long goalId);
}
