package com.security.targets.services;

import com.security.auth.user.models.entities.User;
import com.security.goals.constants.GoalMessages;
import com.security.goals.exceptions.GoalNotFoundException;
import com.security.goals.models.entities.Goal;
import com.security.goals.repositories.GoalRepository;
import com.security.targets.constants.TargetMessages;
import com.security.targets.exceptions.TargetNotFoundException;
import com.security.targets.exceptions.TargetNotUpdatedException;
import com.security.targets.models.dtos.*;
import com.security.targets.models.entities.Target;
import com.security.targets.repositories.TargetRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TargetService {
    private final TargetRepository targetRepository;
    private final GoalRepository goalRepository;
    private final ModelMapper modelMapper;

    //Create new target
    public AddTargetSuccessResponseDTO add(User user, AddTargetDTO addTargetDTO) {

        Goal goal = goalRepository.findByIdAndUserId(addTargetDTO.getGoalId(), user.getId())
                .orElseThrow(() -> new GoalNotFoundException(GoalMessages.ErrorGoalMessages.NOT_FOUND));

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

        TargetDTO updatedTargetDTO = modelMapper.map(updatedTarget, TargetDTO.class);

        return new EditTargetSuccessResponseDTO(
                TargetMessages.SuccessMessages.UPDATED_SUCCESS,
                updatedTargetDTO);
    }

    //Get target by id
    public TargetDTO getTargetById(Long targetId, Long userId) {
        Target target = targetRepository.findByIdAndUserId(targetId, userId)
                .orElseThrow(() -> new TargetNotFoundException(TargetMessages.ErrorMessages.NOT_FOUND));

        //TODO: get total tasks, completed tasks and all target tasks
        return TargetDTO.builder()
                .title(target.getTitle())
                .description(target.getDescription())
                .createdAt(target.getCreatedAt())
                .tasks(new HashSet<>())
                .totalTasks(0)
                .totalCompletedTasks(0)
                .build();
    }

    //Get all goal ID targets
    public Set<TargetDTO> getGoalTargets(Long goalId, Long userId) {

        Set<Target> targets = targetRepository.findAllByGoalIdAndUserId(goalId, userId);

        return targets.stream().map(target -> {
            //TODO count target total and completed tasks
            return TargetDTO.builder()
                    .id(target.getId())
                    .title(target.getTitle())
                    .description(target.getDescription())
                    .totalTasks(0)
                    .totalCompletedTasks(0)
                    .build();
        }).collect(Collectors.toSet());
    }
}
