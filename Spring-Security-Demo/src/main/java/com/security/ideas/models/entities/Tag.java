package com.security.ideas.models.entities;

import com.security.shared.models.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tags")
@NoArgsConstructor
public class Tag extends BaseEntity {

    @NotBlank
    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Idea> ideas = new HashSet<>();

    public Tag(String name) {
        this.name = name.toLowerCase();
    }

    public Tag setName(String name) {
        this.name = name.toLowerCase();
        return this;
    }
}