package com.security.targets.services;

import com.security.auth.user.models.entities.User;
import com.security.goals.constants.GoalMessages;
import com.security.goals.exceptions.GoalNotFoundException;
import com.security.goals.models.entities.Goal;
import com.security.goals.repositories.GoalRepository;
import com.security.targets.constants.TargetMessages;
import com.security.targets.exceptions.*;
import com.security.targets.models.dtos.*;
import com.security.targets.models.entities.Target;
import com.security.targets.repositories.TargetRepository;
import com.security.tasks.models.entities.Task;
import com.security.tasks.models.enums.TaskStatus;
import com.security.tasks.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TargetService {
    private final TargetRepository targetRepository;
    private final TaskRepository taskRepository;
    private final GoalRepository goalRepository;
    private final ModelMapper modelMapper;

    //Create new target
    public AddTargetSuccessResponseDTO add(User user, AddTargetDTO addTargetDTO) {

        try {
            Goal goal = goalRepository.findByIdAndUserId(addTargetDTO.getGoalId(), user.getId())
                    .orElseThrow(() -> new GoalNotFoundException(GoalMessages.ErrorGoalMessages.NOT_FOUND));

            Optional<Target> existingTarget = targetRepository.findByTitleAndGoalIdAndUserId(addTargetDTO.getTitle(), goal.getId(), user.getId());

            if (existingTarget.isPresent()) {
                throw new DuplicateTargetException(TargetMessages.ErrorMessages.DUPLICATE_TARGET);
            }

            Target target = Target.builder()
                    .title(addTargetDTO.getTitle())
                    .description(addTargetDTO.getDescription())
                    .createdAt(LocalDateTime.now())
                    .user(user)
                    .goal(goal)
                    .build();

            Target addedTarget = targetRepository.save(target);

            TargetDTO targetDTO = modelMapper.map(addedTarget, TargetDTO.class);

            return new AddTargetSuccessResponseDTO(
                    TargetMessages.SuccessMessages.ADD_SUCCESS,
                    targetDTO);
        } catch (Exception e) {
            throw new CanNotAddTargetException(TargetMessages.ErrorMessages.ADD_ERROR);
        }
    }

    //Update Target
    public EditTargetSuccessResponseDTO edit(User user, Long targetId, EditTargetDTO editTargetDTO) {

        Target target = targetRepository.findByIdAndUserId(targetId, user.getId())
                .orElseThrow(() -> new TargetNotFoundException(TargetMessages.ErrorMessages.NOT_FOUND));

        boolean isThereTargetChange = false;
        if (editTargetDTO.getTitle() != null && !target.getTitle().equals(editTargetDTO.getTitle())) {
            target.setTitle(editTargetDTO.getTitle());
            isThereTargetChange = true;
        }

        if (editTargetDTO.getDescription() != null && !target.getDescription().equals(editTargetDTO.getDescription())) {
            target.setDescription(editTargetDTO.getDescription());
            isThereTargetChange = true;
        }

        if (!isThereTargetChange) {
            throw new TargetNotUpdatedException(TargetMessages.ErrorMessages.NO_CHANGES_ERROR);
        }

        Target updatedTarget = targetRepository.save(target);

        TargetDTO updatedTargetDTO = TargetDTO.builder()
                .id(updatedTarget.getId())
                .title(updatedTarget.getTitle())
                .description(updatedTarget.getDescription())
                .build();

        return new EditTargetSuccessResponseDTO(
                TargetMessages.SuccessMessages.UPDATED_SUCCESS,
                updatedTargetDTO);

    }

    //Get target by id
    @Transactional
    public TargetDTO getTargetById(Long targetId, Long userId) {
        Target target = targetRepository.findByIdAndUserId(targetId, userId)
                .orElseThrow(() -> new TargetNotFoundException(TargetMessages.ErrorMessages.NOT_FOUND));

        List<Task> tasks = target.getTasks();
        int completedTasks = 0;
        for (Task task : tasks) {
            if (task.getStatus().getStatusName().equals("Completed")) {
                completedTasks++;
            }
        }
        return TargetDTO.builder()
                .id(target.getId())
                .title(target.getTitle())
                .description(target.getDescription())
                .createdAt(target.getCreatedAt())
                .tasks(new ArrayList<>())
                .totalTasks(tasks.size())
                .totalCompletedTasks(completedTasks)
                .build();
    }

    //Get all goal ID targets
    public Set<TargetDTO> getGoalTargets(Long goalId, Long userId) {

        Set<Target> targets = targetRepository.findAllByGoalIdAndUserId(goalId, userId);

        return targets.stream().map(target -> {
            int totalTasks = taskRepository.countTotalTasksByTargetId(target.getId());
            int totalCompletedTasks = taskRepository.countTotalCompletedTasksByTargetId(target.getId());
            return TargetDTO.builder()
                    .id(target.getId())
                    .title(target.getTitle())
                    .description(target.getDescription())
                    .createdAt(target.getCreatedAt())
                    .totalTasks(totalTasks)
                    .totalCompletedTasks(totalCompletedTasks)
                    .build();
        }).collect(Collectors.toSet());
    }
}
