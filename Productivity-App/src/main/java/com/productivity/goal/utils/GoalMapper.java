package com.productivity.goal.utils;

import com.productivity.goal.models.dtos.AddGoalDTO;
import com.productivity.goal.models.dtos.EditGoalDTO;
import com.productivity.goal.models.dtos.GoalDTO;
import com.productivity.goal.models.entities.Goal;
import com.productivity.user.models.entities.User;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Component
public class GoalMapper {

    public static GoalDTO toDTO(Goal goal, Long totalCompletedTargets, Long totalTargets) {
        GoalDTO goalDTO = new GoalDTO();
        goalDTO.setId(goal.getId());
        goalDTO.setTitle(goal.getTitle());
        goalDTO.setDescription(goal.getDescription());
        goalDTO.setCategory(goal.getCategory());
        goalDTO.setCreatedAt(goal.getCreatedAt());
        goalDTO.setDeadline(goal.getDeadline());
        goalDTO.setTargets(new ArrayList<>());
        goalDTO.setTotalCompletedTargets(totalCompletedTargets);
        goalDTO.setTotalTargets(totalTargets);
        return goalDTO;
    }

    public Goal toEntity(AddGoalDTO addGoalDTO, User user) throws ParseException {
        Goal goal = new Goal();
        goal.setTitle(addGoalDTO.getTitle().trim());
        goal.setDescription(addGoalDTO.getDescription().trim());
        goal.setCategory(addGoalDTO.getCategory());
        goal.setCreatedAt(LocalDateTime.now());

        // Convert deadline string to LocalDate object
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("M/d/yyyy");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date inputDate = inputDateFormat.parse(addGoalDTO.getDeadline());
        String outputDateString = outputDateFormat.format(inputDate);
        LocalDate deadline = LocalDate.parse(outputDateString);

        goal.setDeadline(deadline);
        goal.setUser(user);
        return goal;
    }

    public static void updateEntity(Goal goal, EditGoalDTO editGoalDTO) {
        if (editGoalDTO.getTitle() != null) {
            goal.setTitle(editGoalDTO.getTitle().trim());
        }
        if (editGoalDTO.getDescription() != null) {
            goal.setDescription(editGoalDTO.getDescription().trim());
        }
        if (editGoalDTO.getCategory() != null) {
            goal.setCategory(editGoalDTO.getCategory());
        }
        if (editGoalDTO.getDeadline() != null) {
            goal.setDeadline(editGoalDTO.getDeadline());
        }
    }
}

