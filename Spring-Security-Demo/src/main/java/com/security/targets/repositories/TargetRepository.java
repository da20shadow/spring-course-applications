package com.security.targets.repositories;

import com.security.targets.models.entities.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TargetRepository extends JpaRepository<Target,Long> {
    Optional<Target> findByIdAndUserId(Long targetId, Long userId);
}
