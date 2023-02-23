package com.restapi.book.model.entity;

import com.restapi.author.model.entity.AuthorEntity;
import com.restapi.shared.model.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class BookEntity extends BaseEntity {
    private String title;
    private String isbn;
    @ManyToOne
    private AuthorEntity author;
}
