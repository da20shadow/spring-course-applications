package com.security.ideas.models.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AddIdeaDTO {
    @NotBlank(message = "Idea title is required")
    private String title;

    @NotBlank(message = "Idea description is required")
    private String description;

    private Set<String> tags;

    private Long goalId = null;
}
