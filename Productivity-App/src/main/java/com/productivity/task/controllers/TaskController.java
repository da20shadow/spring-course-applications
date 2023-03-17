package com.productivity.task.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @GetMapping("/urgent")
    public ResponseEntity<?> getAllUrgentTasks(){
        Map<String, String> response = new HashMap<>();
        response.put("message", "Coming Soon!");
        return ResponseEntity.badRequest().body(response);
    }
}
