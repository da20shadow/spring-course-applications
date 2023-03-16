package com.productivity.target.models.dtos;

import com.productivity.task.models.dtos.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TargetDTO {

    private Long id;
    private String title;
    private String description;
    private boolean isCompleted;
    private LocalDateTime createdAt;
    private List<TaskDTO> tasks;

    public TargetDTO(Long id, String title, String description,boolean isCompleted, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.createdAt = createdAt;
    }

    public TargetDTO(Long id, String title, String description, LocalDateTime createdAt, List<TaskDTO> taskDTOList) {
    }

}

