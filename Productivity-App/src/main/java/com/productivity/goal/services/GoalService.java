package com.productivity.goal.services;

import com.productivity.goal.exception.GoalException;
import com.productivity.goal.models.dtos.AddGoalDTO;
import com.productivity.goal.models.dtos.EditGoalDTO;
import com.productivity.goal.models.dtos.GoalDTO;
import com.productivity.goal.models.entities.Goal;
import com.productivity.goal.models.enums.GoalCategory;
import com.productivity.goal.repositories.GoalRepository;
import com.productivity.goal.utils.GoalMapper;
import com.productivity.target.models.dtos.TargetDTO;
import com.productivity.target.models.entities.Target;
import com.productivity.target.utils.TargetMapper;
import com.productivity.task.models.entities.Task;
import com.productivity.task.models.enums.TaskStatus;
import com.productivity.user.repositories.UserRepository;
import com.productivity.user.exceptions.UserException;
import com.productivity.user.models.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    @Autowired
    public GoalService(GoalRepository goalRepository, UserRepository userRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
    }

    public Goal addGoal(Long userId, String title, String description, LocalDate deadline, GoalCategory category) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserException.userNotFound(userId));

        if (goalRepository.existsByTitleAndUser_Id(title, userId)) {
            throw new Exception("A goal with this title already exists!");
        }

        Goal goal = new Goal(title, description, category, deadline, user);

        return goalRepository.save(goal);
    }


    public Goal getUserGoal(Long userId, Long goalId) {
        return goalRepository.findByIdAndUserId(goalId, userId)
                .orElseThrow(() -> GoalException.goalNotFound(goalId));
    }

    public Page<GoalDTO> getUserGoalsByCategory(Long userId, GoalCategory category, Pageable pageable) {
        Page<Goal> goalsPage = goalRepository.findByUserIdAndCategory(userId, category, pageable);
        return goalsPage.map(this::getGoalDTOWithCompletedTargetsAndTotalTargets);
    }

    public Page<GoalDTO> getAllUserGoals(Long userId, Pageable pageable) {
        Page<Goal> goalsPage = goalRepository.findByUserId(userId, pageable);
        return goalsPage.map(this::getGoalDTOWithCompletedTargetsAndTotalTargets);
    }

    public Goal editGoal(Long userId, Long goalId, EditGoalDTO editGoalDTO) {
        Goal goal = goalRepository.findByIdAndUserId(goalId, userId)
                .orElseThrow(() -> GoalException.goalNotFound(goalId));

        GoalMapper.updateEntity(goal, editGoalDTO);

        return goalRepository.save(goal);
    }

    public void deleteGoal(Long userId, Long goalId) {
        Goal goal = goalRepository.findByIdAndUserId(goalId, userId)
                .orElseThrow(() -> GoalException.goalNotFound(goalId));

        goalRepository.delete(goal);
    }

    private GoalDTO getGoalDTOWithCompletedTargetsAndTotalTargets(Goal goal) {
        List<TargetDTO> targetDTOs = new ArrayList<>();
        for (Target target : goal.getTargets()) {
            long completedTasksCount = target.getTasks().stream()
                    .filter(task -> task.getStatus() == TaskStatus.COMPLETED)
                    .count();
            boolean isCompleted = completedTasksCount > 0 && completedTasksCount == target.getTasks().size();
            TargetDTO targetDTO = TargetMapper.toDTO(target, isCompleted);
            targetDTOs.add(targetDTO);
        }
        long totalCompletedTargets = targetDTOs.stream()
                .filter(TargetDTO::isCompleted)
                .count();
        long totalTargets = targetDTOs.size();
        return GoalMapper.toDTO(goal, totalCompletedTargets, totalTargets);
    }

}

