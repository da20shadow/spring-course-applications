package com.security.ideas.repositories;

import com.security.ideas.models.entities.Idea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdeaRepository extends JpaRepository<Idea,Long> {

    Optional<Idea> findByTitle(String title);

    List<Idea> findAllByUserId(Long userId);

    Page<Idea> findAllByUserId(Long userId, Pageable pageable);

    Optional<Idea> findByIdAndUserId(Long id,Long userId);

    Page<Idea> findByUserIdAndTags_Id(Long userId,Long tagId, Pageable pageable);

    Page<Idea> findByUserIdAndGoalId(Long userId, Long goalId, Pageable pageable);

    Page<Idea> findByUserIdAndGoalIdAndTags_Id(Long userId, Long goalId, Long tagId, Pageable pageable);

    @Query("SELECT COUNT(i) FROM Idea i WHERE i.goal.id = :goalId")
    int countIdeasByGoalId(@Param("goalId") Long goalId);

    List<Idea> findAllByUserIdAndGoalId(Long userId, Long goalId);

//    List<Idea> findAllByGoalIdAndUserId(Long goalId, Long userId); TODO
}
