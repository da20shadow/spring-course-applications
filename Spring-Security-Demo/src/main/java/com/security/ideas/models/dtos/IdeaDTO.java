package com.security.ideas.models.dtos;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdeaDTO {
    private Long id;
    private String title;
    private String description;
    private Set<String> tags;
    private Long goalId = null;
}
