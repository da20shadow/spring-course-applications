package com.productivity.target.utils;

import com.productivity.target.models.dtos.TargetDTO;
import com.productivity.target.models.entities.Target;
import com.productivity.task.models.dtos.TaskDTO;
import com.productivity.task.utils.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TargetMapper {

    public static TargetDTO toDTO(Target target, boolean isCompleted) {

        TaskMapper taskMapper = new TaskMapper();

        List<TaskDTO> taskDTOList = target.getTasks().stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());

        return new TargetDTO(
                target.getId(),
                target.getTitle(),
                target.getDescription(),
                isCompleted,
                target.getCreatedAt(),
                taskDTOList
        );
    }

}

