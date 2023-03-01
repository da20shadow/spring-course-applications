package com.demorestapi.goal.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddGoalDTO {

    private String title;
    private String description;
    private String dueDate;
}
