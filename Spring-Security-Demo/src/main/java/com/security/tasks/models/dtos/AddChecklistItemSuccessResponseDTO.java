package com.security.tasks.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddChecklistItemSuccessResponseDTO {
    private String message;
    private ChecklistItemDTO item;
}
