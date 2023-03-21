package com.security.ideas.repositories;

import com.security.ideas.models.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String newTagName);
}
