package com.security.tasks.models.dtos;

import com.security.tasks.models.enums.TaskPriority;
import com.security.tasks.models.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddTaskDTO {
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 2, max = 145, message = "Title must be between 2 and 145 characters")
    private String title;
    private String description;
    private String status;
    private String priority;
    private boolean urgent = false;
    private boolean important = false;
    private Long targetId = null;
    private String startDate;
    private String dueDate;

}
