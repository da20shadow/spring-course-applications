package com.cashcard.controllers;

import com.cashcard.models.entities.Task;
import com.cashcard.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class TaskController {

    private TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskRepository.save(task);
        return ResponseEntity.ok(createdTask);
    }


    @GetMapping("/urgent")
    public ResponseEntity<List<Task>> getUrgentTasks(){
        List<Task> tasks = taskRepository.findAll();
//        Task taskOne = new Task(1L,"First Urgent Task Title!","To Do","High");
//        Task taskTwo = new Task(2L,"Second Urgent Task Title!","In Progress","Medium");
//        tasks.add(taskOne);
//        tasks.add(taskTwo);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/all")
    public List<Task> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks;
    }

}
