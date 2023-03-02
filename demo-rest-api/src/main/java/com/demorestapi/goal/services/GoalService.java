package com.demorestapi.goal.services;

import com.demorestapi.goal.models.GoalEntity;
import com.demorestapi.goal.models.dtos.AddGoalDTO;
import com.demorestapi.goal.repositories.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class GoalService {

    private final GoalRepository goalRepository;

    @Autowired
    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public GoalEntity addGoal(AddGoalDTO addGoalDTO) throws ParseException {

        GoalEntity goal = new GoalEntity();
        goal.setTitle(addGoalDTO.getTitle());
        goal.setDescription(addGoalDTO.getDescription());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dueDate = dateFormat.parse(addGoalDTO.getDueDate());
        goal.setDueDate(dueDate);

        return goalRepository.save(goal);
    }

    public List<GoalEntity> getAllGoals(int page) {
        int pageSize = 10;

        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return goalRepository.findAll(pageRequest).getContent();
    }
}
