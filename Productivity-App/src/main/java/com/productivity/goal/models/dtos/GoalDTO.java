package com.productivity.goal.models.dtos;

import com.productivity.goal.models.enums.GoalCategory;
import com.productivity.target.models.entities.Target;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GoalDTO {

    private Long id;
    private String title;
    private String description;
    private GoalCategory category;
    private LocalDateTime createdAt;
    private LocalDate deadline;
    private Long totalCompletedTargets;
    private Long totalTargets;
    private List<Target> targets = new ArrayList<>();

    public GoalDTO() {}

    public GoalDTO(Long id, String title, String description, GoalCategory category, LocalDateTime createdAt, LocalDate deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.createdAt = createdAt;
        this.deadline = deadline;
    }

    public void setTotalCompletedTargets(Long totalCompletedTargets) {
        this.totalCompletedTargets = totalCompletedTargets;
    }

    public void setTotalTargets(Long totalTargets) {
        this.totalTargets = totalTargets;
    }

    public Long getTotalCompletedTargets() {
        return totalCompletedTargets;
    }

    public Long getTotalTargets() {
        return totalTargets;
    }

    public List<Target> getTargets() {
        return targets;
    }

    public GoalDTO setTargets(List<Target> targets) {
        this.targets = targets;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GoalCategory getCategory() {
        return category;
    }

    public void setCategory(GoalCategory category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}

