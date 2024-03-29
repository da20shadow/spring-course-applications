package com.security.tasks.repositories;

import com.security.tasks.models.dtos.TaskDTO;
import com.security.tasks.models.entities.Task;
import com.security.tasks.models.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

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

    //Get today tasks
    @Query("SELECT t FROM Task t JOIN t.user u ON u.id = :userId " +
            "WHERE (FUNCTION('DATE', t.dueDate) = :today " +
                "OR FUNCTION('DATE', t.startDate) = :today " +
                "OR FUNCTION('DATE', t.startDate) < :today AND FUNCTION('DATE', t.dueDate) > :today) " +
            "AND t.status <> 'COMPLETED' " +
            "ORDER BY t.dueDate, t.priority DESC, t.urgent DESC")
    List<Task> findByUserIdAndDueDateOrStartDateAndNotCompleted(Long userId, LocalDate today);

    //Get Overdue tasks
    @Query("SELECT t FROM Task t JOIN t.user u ON u.id = :userId " +
            "WHERE FUNCTION('DATE', t.dueDate) < :today " +
            "AND t.status <> 'COMPLETED' " +
            "ORDER BY t.dueDate, t.priority DESC, t.urgent DESC")
    List<Task> findByUserIdAndDueDateBeforeAndNotCompleted(Long userId, LocalDate today);

    //Get Next tasks
    @Query("SELECT t FROM Task t JOIN t.user u ON u.id = :userId " +
            "WHERE t.status <> 'COMPLETED' " +
            "AND (function('date',t.startDate) > :today " +
                "OR function('date',t.dueDate) > :today AND t.startDate IS NULL) " +
            "ORDER BY t.dueDate, t.priority DESC, t.urgent DESC")
    List<Task> findByUserIdAndDueDateAfterOrStartDateAfter(Long userId, LocalDate today);

    //Get unscheduled tasks TODO: mae it not return COMPLETED!
    List<Task> findByUserIdAndDueDateIsNull(Long userId);

    //Get Important Overdue tasks
    @Query("SELECT t FROM Task t JOIN t.user u ON u.id = :userId " +
            "WHERE FUNCTION('DATE', t.dueDate) < :today " +
            "AND t.status <> 'COMPLETED' AND t.important = TRUE " +
            "ORDER BY t.dueDate, t.priority DESC, t.urgent DESC")
    Page<Task> findByUserIdAndDueDateBeforeAndNotCompletedAndImportantTrue(Long userId, LocalDate today, Pageable pageable);

    //Get Next Important tasks
    @Query("SELECT t FROM Task t JOIN t.user u ON u.id = :userId " +
            "WHERE t.status <> 'COMPLETED' AND t.important = TRUE " +
            "AND (function('date',t.startDate) > :today " +
            "OR function('date',t.dueDate) > :today AND t.startDate IS NULL) " +
            "ORDER BY t.dueDate, t.priority DESC, t.urgent DESC")
    Page<Task> findByUserIdAndDueDateAfterOrStartDateAfterAndImportantTrue(Long userId, LocalDate today, Pageable pageable);

    //Get unscheduled important tasks
    Page<Task> findByUserIdAndDueDateIsNullAndImportantTrue(Long userId, Pageable pageable);

    //Get today important tasks
    @Query("SELECT t FROM Task t JOIN t.user u ON u.id = :userId " +
            "WHERE (FUNCTION('DATE', t.dueDate) = :today " +
            "OR FUNCTION('DATE', t.startDate) = :today " +
            "OR FUNCTION('DATE', t.startDate) < :today AND FUNCTION('DATE', t.dueDate) > :today) " +
            "AND t.status <> 'COMPLETED' AND t.important = TRUE " +
            "ORDER BY t.dueDate, t.priority DESC, t.urgent DESC")
    Page<Task> findByUserIdAndDueDateOrStartDateTodayAndNotCompletedAndImportantTrue(Long userId, LocalDate today, Pageable pageable);

    @Query("SELECT t FROM Task t JOIN t.user u ON u.id = :userId " +
            "WHERE (FUNCTION('YEAR', t.dueDate) = :year AND FUNCTION('MONTH', t.dueDate) = :month " +
            "OR FUNCTION('YEAR', t.startDate) = :year AND FUNCTION('MONTH', t.startDate) = :month ) " +
            "AND t.status <> 'COMPLETED'")
    List<Task> findAllByUserIdAndDueDateYearAndMonthAndNotCompleted(Long userId, int year, int month);

    //Get week tasks from date to date
//    List<Task> findAllByUserIdAndDueDateBetweenOrStartDateBetween(Long userId, LocalDateTime dueDate, LocalDateTime dueDate2, LocalDateTime startDate, LocalDateTime startDate2);

    @Query("SELECT t FROM Task t JOIN t.user u ON u.id = :userId " +
            "WHERE ((t.dueDate BETWEEN :dueDate AND :dueDate2) OR (t.startDate BETWEEN :startDate AND :startDate2)) " +
            "AND t.status <> 'COMPLETED'")
    List<Task> findAllByUserIdAndDueDateBetweenOrStartDateBetween(Long userId,
                                                                  LocalDateTime dueDate,
                                                                  LocalDateTime dueDate2,
                                                                  LocalDateTime startDate,
                                                                  LocalDateTime startDate2);

}
