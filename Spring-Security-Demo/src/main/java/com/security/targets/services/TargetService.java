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

@Service
@RequiredArgsConstructor
public class TargetService {
    private final TargetRepository targetRepository;
    private final GoalRepository goalRepository;
    private final ModelMapper modelMapper;

    public AddTargetSuccessResponseDTO add(User user, AddTargetDTO addTargetDTO) {

        Goal goal = goalRepository.findByIdAndUserId(addTargetDTO.getGoalId(), user.getId())
                .orElseThrow(() -> new GoalNotFoundException(GoalMessages.ErrorGoalMessages.NOT_FOUND));

        Target target = Target.builder()
                .title(addTargetDTO.getTitle())
                .description(addTargetDTO.getDescription())
                .user(user)
                .goal(goal)
                .build();

        Target addedTarget = targetRepository.save(target);

        TargetDTO targetDTO = modelMapper.map(addedTarget, TargetDTO.class);

        return new AddTargetSuccessResponseDTO(
                TargetMessages.SuccessMessages.ADD_SUCCESS,
                targetDTO);
    }

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
}
