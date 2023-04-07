package com.security.goals.models.entities;

import com.security.auth.user.models.entities.User;
import com.security.goals.models.enums.GoalCategory;
import com.security.ideas.models.entities.Idea;
import com.security.shared.models.entities.BaseEntity;
import com.security.targets.models.entities.Target;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "goals", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "title"})
})
public class Goal extends BaseEntity {

    @NotBlank
    @Column(name = "title")
    private String title;

    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private GoalCategory category;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deadline")
    private LocalDate deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Idea> ideas = new HashSet<>();

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Target> targets = new HashSet<>();

    @Transient
    private int totalTargets;

    @Transient
    private int totalCompletedTargets;

    public Goal(String title, String description,GoalCategory category, LocalDate deadline, User user) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.createdAt = LocalDateTime.now(); // Set the creation date to the current date
        this.deadline = deadline;
        this.user = user;
    }

    public void addTarget(Target target) {
        targets.add(target);
        target.setGoal(this);
    }

}

