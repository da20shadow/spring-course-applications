package com.demorestapi.goal.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddGoalDTO {

    @NotEmpty(message = "Title can not be empty! Please, enter Goal Title")
    private String title;
    @NotEmpty(message = "Description can not be empty! Please, enter Goal Description")
    private String description;
    @NotEmpty(message = "Due date can not be empty! Please, enter Goal Due date")
    private String dueDate;

}
