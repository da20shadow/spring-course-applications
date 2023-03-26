package com.security.tasks.models.entities;

import com.security.auth.user.models.entities.User;
import com.security.shared.models.entities.BaseEntity;
import com.security.targets.models.entities.Target;
import com.security.tasks.models.enums.TaskPriority;
import com.security.tasks.models.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tasks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"target_id", "title"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task extends BaseEntity {

    @NotBlank
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
//    @Column(name = "status",columnDefinition = "ENUM('To Do', 'In Progress', 'In revision', 'Completed') DEFAULT('To Do')")
    private TaskStatus status;

    @Enumerated(EnumType.ORDINAL)
//    @Column(name = "priority", columnDefinition = "ENUM('High', 'Medium', 'Low', 'No Priority') DEFAULT('No Priority')")
    private TaskPriority priority;

    @Column(name = "urgent",columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean urgent = false;

    @Column(name = "important",columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean important = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChecklistItem> checklist = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id")
    private Target target;

}


