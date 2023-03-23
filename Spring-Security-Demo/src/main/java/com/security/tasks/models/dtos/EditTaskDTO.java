package com.security.tasks.models.dtos;

import com.security.tasks.models.enums.TaskPriority;
import com.security.tasks.models.enums.TaskStatus;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditTaskDTO {
    @Size(min = 2, max = 145, message = "Title must be between 2 and 145 characters")
    private String title;

    @Size(max = 1024, message = "Description cannot exceed 1024 characters")
    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private boolean urgent;

    private boolean important;

    private LocalDateTime startDate;

    private LocalDateTime dueDate;

}

