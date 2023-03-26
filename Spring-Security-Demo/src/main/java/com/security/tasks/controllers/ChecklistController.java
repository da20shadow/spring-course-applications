package com.security.tasks.controllers;

import com.security.auth.user.models.entities.User;
import com.security.shared.models.dtos.ErrorResponseDTO;
import com.security.tasks.models.dtos.AddChecklistItemDTO;
import com.security.tasks.models.dtos.ChecklistItemDTO;
import com.security.tasks.models.dtos.EditChecklistItemDTO;
import com.security.tasks.services.ChecklistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/checklists")
@RequiredArgsConstructor
public class ChecklistController {
    private final ChecklistService checklistService;

    @PostMapping("/tasks/{taskId}")
    public ResponseEntity<?> addChecklistItem(@PathVariable Long taskId,
                                              @RequestBody @Valid AddChecklistItemDTO checklistItem,
                                              Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok().body(checklistService.add(taskId, user, checklistItem));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<?> editChecklistItem(@PathVariable Long itemId,
                                               @Valid @RequestBody EditChecklistItemDTO item, BindingResult result,
                                               Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok().body(checklistService.editItem(itemId, user.getId(), item));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> deleteChecklistItem(@PathVariable Long itemId,
                                              Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok().body(checklistService.deleteItem(itemId, user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }


    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<?> getTaskChecklistItems(@PathVariable Long taskId, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(checklistService.getAllByTaskId(taskId,user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @GetMapping("/tasks/{taskId}/completed")
    public ResponseEntity<?> getCompletedTaskChecklistItems(@PathVariable Long taskId, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(checklistService.getAllByTaskId(taskId,user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }
}
