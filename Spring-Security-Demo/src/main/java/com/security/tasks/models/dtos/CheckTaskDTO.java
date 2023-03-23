package com.security.tasks.models.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckTaskDTO {
    private String title;
    private String description;
    private boolean completed;
}
