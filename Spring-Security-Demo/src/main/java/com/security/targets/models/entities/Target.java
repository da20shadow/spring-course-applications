package com.security.targets.models.entities;


import com.security.auth.user.models.entities.User;
import com.security.goals.models.entities.Goal;
import com.security.shared.models.entities.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "targets", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"goal_id", "title"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Target extends BaseEntity {

    @NotBlank
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    private Goal goal;

//    @OneToMany(mappedBy = "target", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Task> tasks = new ArrayList<>();

}


