package com.productivity.goal.models.entities;

import com.productivity.goal.models.enums.GoalCategory;
import com.productivity.idea.models.entities.Idea;
import com.productivity.shared.models.BaseEntity;
import com.productivity.target.models.entities.Target;
import com.productivity.user.models.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "goals", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "title"})
})
public class Goal extends BaseEntity {

    @NotBlank
    @Column(name = "title")
    private String title;

    @Column(name = "description")
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
    private List<Idea> ideas = new ArrayList<>();

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Target> targets = new ArrayList<>();

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
