package com.security.goals.services;

import com.security.auth.user.models.entities.User;
import com.security.goals.constants.GoalMessages;
import com.security.goals.exceptions.DuplicateGoalException;
import com.security.goals.exceptions.GoalNotFoundException;
import com.security.goals.models.dtos.AddGoalDTO;
import com.security.goals.models.dtos.GoalDTO;
import com.security.goals.models.entities.Goal;
import com.security.goals.repositories.GoalRepository;
import com.security.ideas.repositories.IdeaRepository;
import com.security.targets.models.dtos.TargetDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GoalService {
    private final GoalRepository goalRepository;
    private final IdeaRepository ideaRepository;
    private final ModelMapper modelMapper;

    public GoalDTO add(User user, AddGoalDTO addGoalDTO) throws Exception {

        if (goalRepository.existsByTitleAndUser_Id(addGoalDTO.getTitle(), user.getId())) {
            throw new DuplicateGoalException(GoalMessages.ErrorGoalMessages.DUPLICATE_TITLE_ERROR);
        }

        Goal goal = modelMapper.map(addGoalDTO, Goal.class);
        goal.setUser(user);
        goal.setCreatedAt(LocalDateTime.now());
        Goal createdGoal = goalRepository.save(goal);
        return modelMapper.map(createdGoal, GoalDTO.class);
    }

    public Page<GoalDTO> getAllUserGoals(Long userId, Pageable pageable) {
        Page<Goal> goalsPage = goalRepository.findByUserId(userId, pageable);
        return goalsPage.map(goal -> {
            return GoalDTO.builder()
                    .id(goal.getId())
                    .title(goal.getTitle())
                    .description(goal.getDescription())
                    .category(goal.getCategory())
                    .createdAt(goal.getCreatedAt())
                    .deadline(goal.getDeadline())
                    .targets(new HashSet<>())
                    .totalIdeas(3)
                    .totalTargets(0)
                    .totalCompletedTargets(0)
                    .build();
        });
//        return goalsPage.map(this::getGoalDTOWithCompletedTargetsAndTotalTargets);
    }

    public GoalDTO getGoalById(Long goalId, Long userId) {
        Optional<Goal> optionalGoal = goalRepository.findByIdAndUserId(goalId,userId);
        if (optionalGoal.isEmpty()) {
            throw new GoalNotFoundException(GoalMessages.ErrorGoalMessages.NOT_FOUND);
        }
        int totalIdeas = ideaRepository.countIdeasByGoalId(goalId);
        return GoalDTO.builder()
                .id(optionalGoal.get().getId())
                .title(optionalGoal.get().getTitle())
                .description(optionalGoal.get().getDescription())
                .category(optionalGoal.get().getCategory())
                .createdAt(optionalGoal.get().getCreatedAt())
                .deadline(optionalGoal.get().getDeadline())
                .targets(new HashSet<>())
                .totalIdeas(totalIdeas)
                .totalTargets(0)
                .totalCompletedTargets(0)
                .build();
    }

//    private GoalDTO getGoalDTOWithCompletedTargetsAndTotalTargets(Goal goal) {
//        List<TargetDTO> targetDTOs = new ArrayList<>();
//        for (Target target : goal.getTargets()) {
//            long completedTasksCount = target.getTasks().stream()
//                    .filter(task -> task.getStatus() == TaskStatus.COMPLETED)
//                    .count();
//            boolean isCompleted = completedTasksCount > 0 && completedTasksCount == target.getTasks().size();
//            TargetDTO targetDTO = TargetMapper.toDTO(target, isCompleted);
//            targetDTOs.add(targetDTO);
//        }
//        long totalCompletedTargets = targetDTOs.stream()
//                .filter(TargetDTO::isCompleted)
//                .count();
//        long totalTargets = targetDTOs.size();
//        return GoalMapper.toDTO(goal, totalCompletedTargets, totalTargets);
//    }
}
