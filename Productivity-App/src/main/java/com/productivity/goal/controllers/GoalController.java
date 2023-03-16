package com.productivity.goal.controllers;

import com.productivity.config.JwtService;
import com.productivity.goal.models.dtos.AddGoalDTO;
import com.productivity.goal.models.dtos.EditGoalDTO;
import com.productivity.goal.models.dtos.GoalDTO;
import com.productivity.goal.models.entities.Goal;
import com.productivity.goal.models.enums.GoalCategory;
import com.productivity.goal.services.GoalService;
import com.productivity.goal.utils.GoalMapper;
import com.productivity.user.models.entities.User;
import com.productivity.user.repositories.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/goals")
public class GoalController {

    private final GoalService goalService;
    private final UserRepository userRepository;

    @Autowired
    public GoalController(GoalService goalService, UserRepository userRepository) {
        this.goalService = goalService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> addGoal(@RequestBody @Valid AddGoalDTO addGoalDTO,
                                        Authentication authentication) {

        try {
            String userEmail = authentication.getName();
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            Long userId = user.getId();

            // Convert deadline string to LocalDate object
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("M/d/yyyy");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date inputDate = inputDateFormat.parse(addGoalDTO.getDeadline());
            String outputDateString = outputDateFormat.format(inputDate);
            LocalDate deadline = LocalDate.parse(outputDateString);

            Goal goal = goalService.addGoal(userId, addGoalDTO.getTitle(),
                    addGoalDTO.getDescription(), deadline, addGoalDTO.getCategory());

            return ResponseEntity.status(HttpStatus.CREATED).body(goal);
        } catch (Exception exception) {
            Map<String, String> errorMsg = new HashMap<>();
            errorMsg.put("message", exception.getMessage());
            return ResponseEntity.badRequest().body(errorMsg);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalDTO> getGoalById(@PathVariable Long id,Authentication authentication) {

        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Long userId = user.getId();

        Goal goal = goalService.getUserGoal(userId,id);
        System.out.println("Goal Title: "+goal.getTitle());
        GoalDTO goalDTO = GoalMapper.toDTO(goal, 0L, 0L);
        return ResponseEntity.ok(goalDTO);
    }

    @GetMapping("/category")
    public ResponseEntity<Page<GoalDTO>> getGoalsByCategory(@RequestParam GoalCategory category,
                                                            @RequestParam(defaultValue = "0") int page) {
        // TODO: get userID from the session
        Page<GoalDTO> goalsPage = goalService
                .getUserGoalsByCategory(1L,
                        category,
                        PageRequest.of(page,
                                25,
                                Sort.by("createdAt").descending()));
        return ResponseEntity.ok(goalsPage);
    }

    @GetMapping
    public ResponseEntity<Page<GoalDTO>> getAllGoals(@RequestParam(defaultValue = "0") int page) {
        Page<GoalDTO> goalsPage = goalService
                .getAllUserGoals(1L, PageRequest.of(page, 25, Sort.by("createdAt").descending()));
        return ResponseEntity.ok(goalsPage);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GoalDTO> editGoal(@PathVariable Long id,
                                            @RequestBody @Valid EditGoalDTO editGoalDTO) {
        //TODO: get user from session
        Goal goal = goalService.editGoal(1L, id, editGoalDTO);
        GoalDTO goalDTO = GoalMapper.toDTO(goal,0L,0L);
        return ResponseEntity.ok(goalDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(1L, id);
        return ResponseEntity.noContent().build();
    }

    // Handle exceptions

//    @ExceptionHandler({GoalException.class, MethodArgumentNotValidException.class})
//    public ResponseEntity<ErrorResponse> handleGoalException(Exception exception) {
//        String message = exception.getMessage();
//        if (exception instanceof MethodArgumentNotValidException) {
//            message = ((MethodArgumentNotValidException) exception).getFieldError().getDefaultMessage();
//        }
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
//                message, LocalDateTime.now());
//        return ResponseEntity.badRequest().body(errorResponse);
//    }
}

