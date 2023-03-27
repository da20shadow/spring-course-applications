package com.security.tasks.repositories;

import com.security.tasks.models.entities.Task;
import com.security.tasks.models.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("SELECT COUNT(t) as totalTasks " +
            "FROM Task t WHERE t.target.id = :targetId")
    int countTotalTasksByTargetId(Long targetId);

    @Query("SELECT COUNT(t) as totalCompletedTasks " +
            "FROM Task t WHERE t.target.id = :targetId AND t.status = 'Completed'")
    int countTotalCompletedTasksByTargetId(Long targetId);

    Page<Task> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    Page<Task> findByUserIdAndImportantTrue(Long userId, Pageable pageable);

    Page<Task> findByUserIdAndUrgentTrue(Long userId, Pageable pageable);

    Page<Task> findByUserIdAndTargetId(Long userId, Long targetId, Pageable pageable);

    Page<Task> findByUserIdAndTargetIdAndStatusNot(Long userId, Long targetId, TaskStatus status, Pageable pageable);

    List<Task> findByUserIdAndTargetId(Long userId, Long targetId);

    Optional<Task> findByIdAndUserId(Long taskId, Long userId);

    Optional<Task> findByTitleAndUserId(String title, Long userId);

    Optional<Task> findByTitleAndTargetIdAndUserId(String title, Long targetId, Long userId);

    @Query("SELECT t FROM Task t JOIN t.user u WHERE FUNCTION('DATE', t.dueDate) = :today AND u.id = :userId")
    List<Task> findAllByDueDateAndUserId(LocalDate today, Long userId);
}
