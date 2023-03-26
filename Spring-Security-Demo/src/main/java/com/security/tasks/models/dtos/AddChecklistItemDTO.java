package com.security.tasks.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddChecklistItemDTO {
    @NotNull(message = "Title can not be empty!")
    private String title;
    private final boolean completed = false;
}
