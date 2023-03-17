package com.productivity.task.utils;

import com.productivity.task.models.dtos.TaskDTO;
import com.productivity.task.models.entities.Task;

public class TaskMapper {

    public TaskDTO toDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setStartDate(task.getStartDate());
        dto.setEndDate(task.getEndDate());
        dto.setUserId(task.getUser().getId());
        dto.setTargetId(task.getTarget().getId());
        return dto;
    }

    public static Task toEntity(TaskDTO dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setCreatedAt(dto.getCreatedAt());
        task.setStartDate(dto.getStartDate());
        task.setEndDate(dto.getEndDate());
        return task;
    }

}

