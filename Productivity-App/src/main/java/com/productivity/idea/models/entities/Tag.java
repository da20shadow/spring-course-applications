package com.productivity.idea.models.entities;

import com.productivity.shared.models.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Tag extends BaseEntity {

    @NotBlank
    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Idea> ideas = new HashSet<>();

    public Tag(String name) {
        this.name = name.toLowerCase();
    }

}

