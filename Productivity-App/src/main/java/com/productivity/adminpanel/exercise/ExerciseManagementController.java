package com.productivity.adminpanel.exercise;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/management/api/v1/exercises")
public class ExerciseManagementController {

    @GetMapping
    public ResponseEntity<?> getAllExercises(){
        Map<String, String> exercises = new HashMap<>();
        exercises.put("Push Up", "Push ups description");
        exercises.put("Pull Up", "Pull ups description");
        exercises.put("Sit Up", "Sit ups description");
        return ResponseEntity.ok(exercises);
    }

}
