package com.security.tasks.controllers;

import com.security.auth.user.models.entities.User;
import com.security.shared.models.dtos.ErrorResponseDTO;
import com.security.shared.models.dtos.SuccessResponseDTO;
import com.security.tasks.constants.TaskMessages;
import com.security.tasks.models.dtos.AddTaskDTO;
import com.security.tasks.models.dtos.EditTaskDTO;
import com.security.tasks.models.dtos.TaskAndMessageResponseDTO;
import com.security.tasks.models.dtos.TaskDTO;
import com.security.tasks.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<?> addTask(@Valid @RequestBody AddTaskDTO addTaskDTO,
                                     BindingResult bindingResult,
                                     Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(Objects.requireNonNull(bindingResult.getFieldError())
                    .getDefaultMessage());
        }
        try {
            User user = (User) authentication.getPrincipal();
            TaskDTO task = taskService.addTask(addTaskDTO, user);
            return ResponseEntity.status(201).body(new TaskAndMessageResponseDTO(
                    TaskMessages.SuccessMessages.ADD_SUCCESS,
                    task
            ));
        } catch (Exception e) {
            //TODO: remove the log
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id,
                                        @Valid @RequestBody EditTaskDTO editTaskDTO,
                                        BindingResult bindingResult,
                                        Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(Objects.requireNonNull(bindingResult.getFieldError())
                            .getDefaultMessage());
        }
        try {
            User user = (User) authentication.getPrincipal();
            TaskDTO task = taskService.updateTask(id, editTaskDTO, user.getId());
            return ResponseEntity.ok(new TaskAndMessageResponseDTO(
                    TaskMessages.SuccessMessages.UPDATE_SUCCESS,
                    task
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(TaskMessages.ErrorMessages.UPDATE_ERROR));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            taskService.deleteTask(id, user.getId());
            return ResponseEntity.ok(new SuccessResponseDTO(TaskMessages.SuccessMessages.DELETE_SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(TaskMessages.ErrorMessages.DELETE_ERROR));
        }
    }

    @GetMapping("/today")
    public ResponseEntity<?> getAllTasksWithEndDateToday(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(taskService.getTasksWithEndDateToday(user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(TaskMessages.ErrorMessages.NO_TODAY_TASKS));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id,Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(taskService.getTaskByIdAndUserId(id,user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            Authentication authentication) {

        try {
            User user = (User) authentication.getPrincipal();
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
            Page<TaskDTO> tasksPage = taskService.getAllTasks(user.getId(), pageable);
            return ResponseEntity.ok(tasksPage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @GetMapping("/important")
    public ResponseEntity<?> getImportantTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            Authentication authentication) {

        try {
            User user = (User) authentication.getPrincipal();

            Pageable pageable = PageRequest.of(page,
                    size, Sort.by("important").descending()
                            .and(Sort.by("createdAt").ascending()));

            Page<TaskDTO> tasksPage = taskService.getImportantTasks(user.getId(), pageable);
            return ResponseEntity.ok(tasksPage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @GetMapping("/urgent")
    public ResponseEntity<?> getUrgentTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            Authentication authentication) {

        try {
            User user = (User) authentication.getPrincipal();
            Pageable pageable = PageRequest.of(page, size, Sort.by("urgent").descending());
            Page<TaskDTO> tasksPage = taskService.getUrgentTasks(user.getId(), pageable);
            return ResponseEntity.ok(tasksPage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @GetMapping("/targets/{targetId}")
    public ResponseEntity<?> getTasksByTargetId(
            @PathVariable Long targetId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            Authentication authentication) {

        try {
            User user = (User) authentication.getPrincipal();
            Pageable pageable = PageRequest.of(page, size, Sort.by(
                    Sort.Order.desc("priority"),
                    Sort.Order.asc("dueDate").nullsLast()
            ));

            Page<TaskDTO> tasksPage = taskService.getTasksByTargetId(user.getId(), targetId, pageable);
            return ResponseEntity.ok(tasksPage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

}
