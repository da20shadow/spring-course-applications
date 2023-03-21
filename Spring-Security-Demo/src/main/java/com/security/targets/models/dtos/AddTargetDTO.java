package com.security.targets.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddTargetDTO {
    @NotNull(message = "Title can not be empty!")
    private String title;
    @NotNull(message = "Description can not be empty!")
    private String description;
    @NotNull(message = "Goal ID can not be empty!")
    private Long goalId;
}
