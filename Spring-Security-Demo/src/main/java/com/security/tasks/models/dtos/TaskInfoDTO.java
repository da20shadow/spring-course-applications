package com.security.tasks.models.dtos;

import com.security.tasks.models.entities.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskInfoDTO {
    private List<?> overdueTasksList;
    private int overdueTasks;
    private List<?> nextTasksList;
    private int nextTasks;
    private List<?> unscheduledTasksList;
    private int unscheduledTasks;
}

