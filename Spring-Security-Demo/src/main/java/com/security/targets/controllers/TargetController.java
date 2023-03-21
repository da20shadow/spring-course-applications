package com.security.targets.controllers;

import com.security.auth.user.models.entities.User;
import com.security.shared.models.dtos.ErrorResponseDTO;
import com.security.targets.models.dtos.AddTargetDTO;
import com.security.targets.models.dtos.EditTargetDTO;
import com.security.targets.models.dtos.TargetDTO;
import com.security.targets.services.TargetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/targets")
@RequiredArgsConstructor
public class TargetController {

    private final TargetService targetService;

    @PostMapping
    public ResponseEntity<?> addNewTarget(@Valid @RequestBody AddTargetDTO addTargetDTO,
                                          BindingResult result,
                                          Authentication authentication) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errors.get(0)));
        }
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(targetService.add(user, addTargetDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @PatchMapping("/{targetId}")
    public ResponseEntity<?> editTarget(@Valid @RequestBody EditTargetDTO editTargetDTO,
                                        BindingResult result,
                                        @PathVariable Long targetId,
                                        Authentication authentication) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errors.get(0)));
        }
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(targetService.edit(user, targetId, editTargetDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }
}
