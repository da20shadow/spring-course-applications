package com.demorestapi.goal.controllers;

import com.demorestapi.goal.models.GoalEntity;
import com.demorestapi.goal.models.dtos.AddGoalDTO;
import com.demorestapi.goal.services.GoalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/goals")
//TODO: add only "http://localhost:3000/"
@CrossOrigin(origins = "*", maxAge = 3600,
        allowedHeaders={"x-auth-token", "x-requested-with", "x-xsrf-token"})
public class GoalController {

    private final GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping("")
    private ResponseEntity<List<GoalEntity>> getGoals(@RequestParam(defaultValue = "0") int page) {
        List<GoalEntity> goalsList = goalService.getAllGoals(page);
        return ResponseEntity.ok(goalsList);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addGoal(@Valid @RequestBody AddGoalDTO addGoalDTO, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                errors.append(error.getField() + ": " + error.getDefaultMessage() + "\n");
            }
            return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
        }
        try {
            GoalEntity savedGoal = goalService.addGoal(addGoalDTO);
            return new ResponseEntity<>(savedGoal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Something get wrong please try again!", HttpStatus.BAD_REQUEST);
        }
    }
}
