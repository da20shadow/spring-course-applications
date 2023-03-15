package com.productivity.idea.models.entities;

import com.productivity.goal.models.entities.Goal;
import com.productivity.shared.models.BaseEntity;
import com.productivity.user.models.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ideas")
public class Idea extends BaseEntity {
    @NotBlank(message = "Title can not be empty!")
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "idea_tag",
            joinColumns = @JoinColumn(name = "idea_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_name"))
    private Set<Tag> tags = new HashSet<>();

    /** addTag method checks whether a tag with the same name already exists in the tags set.
     * If it does, the existing tag is used instead of creating a new one.
     * Otherwise, a new tag is created and added to the set.
     * With these modifications, users can add tags to their Ideas using the same name as existing tags,
     * and the tags will be shared between users.
     * */
    public void addTag(Tag tag) {
        Tag existingTag = tags.stream()
                .filter(t -> t.getName().equals(tag.getName()))
                .findFirst()
                .orElse(null);
        if (existingTag != null) {
            tags.add(existingTag);
        } else {
            tags.add(tag);
        }
    }
}
