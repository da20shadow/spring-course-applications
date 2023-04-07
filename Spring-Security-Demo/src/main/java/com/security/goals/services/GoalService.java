package com.security.goals.services;

import com.security.auth.user.models.entities.User;
import com.security.goals.constants.GoalMessages;
import com.security.goals.exceptions.DuplicateGoalException;
import com.security.goals.exceptions.GoalNotFoundException;
import com.security.goals.exceptions.GoalNotUpdatedException;
import com.security.goals.models.dtos.AddGoalDTO;
import com.security.goals.models.dtos.GoalDTO;
import com.security.goals.models.dtos.UpdateGoalDTO;
import com.security.goals.models.entities.Goal;
import com.security.goals.models.enums.GoalCategory;
import com.security.goals.repositories.GoalRepository;
import com.security.ideas.repositories.IdeaRepository;
import com.security.shared.models.dtos.SuccessResponseDTO;
import com.security.targets.models.entities.Target;
import com.security.targets.repositories.TargetRepository;
import com.security.tasks.models.entities.Task;
import com.security.tasks.models.enums.TaskStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GoalService {
    private final GoalRepository goalRepository;
    private final IdeaRepository ideaRepository;
    private final TargetRepository targetRepository;
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

    @Transactional
    public GoalDTO update(Long goalId,User user, UpdateGoalDTO updateGoalDTO) throws Exception {
        Optional<Goal> optionalGoal = goalRepository.findByIdAndUserId(goalId, user.getId());
        if (optionalGoal.isEmpty()) {
            throw new DuplicateGoalException(GoalMessages.ErrorGoalMessages.NOT_FOUND);
        }
        if (goalRepository.existsByTitleAndUser_Id(updateGoalDTO.getTitle(), user.getId())) {
            throw new DuplicateGoalException(GoalMessages.ErrorGoalMessages.DUPLICATE_TITLE_ERROR);
        }
        Goal goal = optionalGoal.get();
        boolean hasUpdate = false;
        if (updateGoalDTO.getTitle() != null) {
            goal.setTitle(updateGoalDTO.getTitle());
            hasUpdate = true;
        }
        if (updateGoalDTO.getDescription() != null) {
            goal.setDescription(updateGoalDTO.getDescription());
            hasUpdate = true;
        }
        if (updateGoalDTO.getDeadline() != null) {
            LocalDate deadline = LocalDate.parse(updateGoalDTO.getDeadline());
            goal.setDeadline(deadline);
            hasUpdate = true;
        }
        if (!hasUpdate) {
            throw new GoalNotUpdatedException(GoalMessages.ErrorGoalMessages.NO_CHANGES);
        }
        return getGoalById(goalId, user.getId());
    }

    @Transactional
    public Page<GoalDTO> getAllUserGoals(Long userId, Pageable pageable) {
        Page<Goal> goalsPage = goalRepository.findByUserId(userId, pageable);
        return goalsPage.map(goal -> {

            Set<Target> targets = goal.getTargets();
            int totalCompletedTargets = 0;

            for (Target target : targets) {
                List<Task> tasks = target.getTasks();
                int totalCompletedTasks = 0;
                for (Task task : tasks) {
                    if (task.getStatus().getStatusName().equals("Completed")) {
                        totalCompletedTasks++;
                    }
                }
                if (tasks.size() > 0 && totalCompletedTasks == tasks.size()) {
                    totalCompletedTargets++;
                }
            }

            return GoalDTO.builder()
                    .id(goal.getId())
                    .title(goal.getTitle())
                    .description(goal.getDescription())
                    .category(goal.getCategory())
                    .createdAt(goal.getCreatedAt())
                    .deadline(goal.getDeadline())
                    .targets(new HashSet<>())
                    .totalTargets(targets.size())
                    .totalCompletedTargets(totalCompletedTargets)
                    .build();
        });
//        return goalsPage.map(this::getGoalDTOWithCompletedTargetsAndTotalTargets);
    }

    @Transactional
    public Page<GoalDTO> getAllUserGoalsByCategory(Long userId, GoalCategory category, Pageable pageable) {
        Page<Goal> goalsPage = goalRepository.findByUserIdAndCategory(userId, category,pageable);
        return goalsPage.map(goal -> {

            Set<Target> targets = goal.getTargets();
            int totalCompletedTargets = 0;

            for (Target target : targets) {
                List<Task> tasks = target.getTasks();
                int totalCompletedTasks = 0;
                for (Task task : tasks) {
                    if (task.getStatus().getStatusName().equals("Completed")) {
                        totalCompletedTasks++;
                    }
                }
                if (tasks.size() > 0 && totalCompletedTasks == tasks.size()) {
                    totalCompletedTargets++;
                }
            }

            return GoalDTO.builder()
                    .id(goal.getId())
                    .title(goal.getTitle())
                    .description(goal.getDescription())
                    .category(goal.getCategory())
                    .createdAt(goal.getCreatedAt())
                    .deadline(goal.getDeadline())
                    .targets(new HashSet<>())
                    .totalTargets(targets.size())
                    .totalCompletedTargets(totalCompletedTargets)
                    .build();
        });
    }

    @Transactional
    public GoalDTO getGoalById(Long goalId, Long userId) {
        Optional<Goal> optionalGoal = goalRepository.findByIdAndUserId(goalId, userId);
        if (optionalGoal.isEmpty()) {
            throw new GoalNotFoundException(GoalMessages.ErrorGoalMessages.NOT_FOUND);
        }
        int totalIdeas = ideaRepository.countIdeasByGoalId(goalId);
        Set<Target> targets = optionalGoal.get().getTargets();
        int totalCompletedTargets = 0;
        for (Target target : targets) {
            List<Task> tasks = target.getTasks();
            int totalCompletedTasks = 0;
            for (Task task : tasks) {
                if (task.getStatus().getStatusName().equals("Completed")) {
                    totalCompletedTasks++;
                }
            }
            if (tasks.size() > 0 && totalCompletedTasks == tasks.size()) {
                totalCompletedTargets++;
            }
        }
        //TODO: get all goal targets
        return GoalDTO.builder()
                .id(optionalGoal.get().getId())
                .title(optionalGoal.get().getTitle())
                .description(optionalGoal.get().getDescription())
                .category(optionalGoal.get().getCategory())
                .createdAt(optionalGoal.get().getCreatedAt())
                .deadline(optionalGoal.get().getDeadline())
                .totalIdeas(totalIdeas)
                .totalCompletedTargets(totalCompletedTargets)
                .totalTargets(targets.size())
                .build();
    }

    public SuccessResponseDTO deleteGoal(Long goalId, Long userId) {
        Goal goal = goalRepository.findByIdAndUserId(goalId, userId)
                .orElseThrow(()-> new GoalNotFoundException(GoalMessages.ErrorGoalMessages.NOT_FOUND));
        goalRepository.delete(goal);
        return new SuccessResponseDTO(GoalMessages.SuccessGoalMessages.DELETED_SUCCESS);
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
