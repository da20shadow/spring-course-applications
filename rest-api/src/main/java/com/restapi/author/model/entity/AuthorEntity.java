package com.restapi.author.model.entity;

import com.restapi.book.model.entity.BookEntity;
import com.restapi.shared.model.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class AuthorEntity extends BaseEntity {
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @OneToMany
    private List<BookEntity> books;
}
