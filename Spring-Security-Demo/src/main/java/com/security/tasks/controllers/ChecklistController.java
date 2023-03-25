package com.security.tasks.controllers;

import com.security.auth.user.models.entities.User;
import com.security.shared.models.dtos.ErrorResponseDTO;
import com.security.tasks.services.ChecklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/checklists")
@RequiredArgsConstructor
public class ChecklistController {
    private final ChecklistService checklistService;

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTaskChecklistItems(@PathVariable Long taskId, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(checklistService.getAllByTaskId(taskId,user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @GetMapping("/{taskId}/completed")
    public ResponseEntity<?> getCompletedTaskChecklistItems(@PathVariable Long taskId, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(checklistService.getAllByTaskId(taskId,user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }
}
