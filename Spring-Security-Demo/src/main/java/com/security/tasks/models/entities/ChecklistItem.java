package com.security.tasks.models.entities;

import com.security.auth.user.models.entities.User;
import com.security.shared.models.entities.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;


@Entity
@Table(name = "checklists", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"task_id", "title"})
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChecklistItem extends BaseEntity {

    @NotBlank
    @Column(name = "title")
    private String title;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean completed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
