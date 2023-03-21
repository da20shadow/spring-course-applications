package com.productivity.goal.models.dtos;

import com.productivity.goal.models.enums.GoalCategory;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddGoalDTO {

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 145, message = "Title must be between 5 and 145 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 5, message = "Description must be at least 5 characters")
    private String description;

    @NotNull(message = "Deadline is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String deadline;

    @NotNull(message = "Category is required")
    private GoalCategory category;

}

