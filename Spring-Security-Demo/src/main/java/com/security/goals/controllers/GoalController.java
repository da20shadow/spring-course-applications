package com.security.goals.controllers;

import com.security.auth.user.models.entities.User;
import com.security.goals.constants.GoalMessages;
import com.security.goals.models.dtos.AddGoalDTO;
import com.security.goals.models.dtos.AddGoalSuccessResponseDTO;
import com.security.goals.models.dtos.GoalDTO;
import com.security.goals.services.GoalService;
import com.security.shared.models.dtos.ErrorResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @PostMapping
    public ResponseEntity<?> addGoal(@Valid @RequestBody AddGoalDTO addGoalDTO,
                                     BindingResult result,
                                     Authentication authentication) {

        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDTO(errors.get(0)));
        }

        try {
            User user = (User) authentication.getPrincipal();
            GoalDTO goal = goalService.add(user, addGoalDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AddGoalSuccessResponseDTO(
                            GoalMessages.SuccessGoalMessages.ADD_SUCCESS,
                            goal));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
        }
    }

    @DeleteMapping("/{goalId}")
    public ResponseEntity<?> deleteGoal(@PathVariable Long goalId, Authentication authentication) {
        System.out.println(goalId);
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(goalService.deleteGoal(goalId,user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllGoals(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "25") int size,
                                         Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
            return ResponseEntity.ok(goalService.getAllUserGoals(user.getId(), pageable));
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGoalById(@PathVariable Long id,
                                               Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        try {
            GoalDTO goal = goalService.getGoalById(id,user.getId());
            return ResponseEntity.ok(goal);
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }
}

