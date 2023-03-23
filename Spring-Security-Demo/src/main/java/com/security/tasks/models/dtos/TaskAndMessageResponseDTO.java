package com.security.tasks.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TaskAndMessageResponseDTO {
    private String message;
    private TaskDTO task;
}
