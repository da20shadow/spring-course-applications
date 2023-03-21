package com.security.goals.models.dtos;

import com.security.auth.user.models.dtos.UserDTO;
import com.security.goals.models.enums.GoalCategory;
import com.security.ideas.models.dtos.IdeaDTO;
import com.security.targets.models.dtos.TargetDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalDTO {
    private Long id;
    private String title;
    private String description;
    private GoalCategory category;
    private LocalDateTime createdAt;
    private LocalDate deadline;
    private UserDTO user;
    private int totalTargets;
    private int totalCompletedTargets;
    private int totalIdeas;
    private Set<TargetDTO> targets;
}
