package com.security.targets.models.dtos;

import com.security.tasks.models.entities.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TargetDTO {
    private String title;
    private String description;
    private int totalTasks;
    private int totalCompletedTasks;
    private Set<Task> tasks;
}
