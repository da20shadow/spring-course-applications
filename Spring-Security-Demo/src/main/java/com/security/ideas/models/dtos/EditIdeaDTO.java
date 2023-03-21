package com.security.ideas.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class EditIdeaDTO {
    private String title;
    private String description;
    private Set<String> tags;

    public EditIdeaDTO() {
        this.title = "";
        this.description = "";
        this.tags = new HashSet<>();
    }

    public EditIdeaDTO(String title) {
        this.title = title;
    }

    public EditIdeaDTO(Set<String> tags) {
        this.tags = tags;
    }

    public EditIdeaDTO(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public EditIdeaDTO(String title, String description, Set<String> tags) {
        this.title = title;
        this.description = description;
        this.tags = tags;
    }
}
