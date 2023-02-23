package com.restapi.author;

import com.restapi.author.model.entity.AuthorEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    @GetMapping
    public ResponseEntity<List<AuthorEntity>> getAll() {
        AuthorEntity author = new AuthorEntity();
        AuthorEntity author2 = new AuthorEntity();
        AuthorEntity author3 = new AuthorEntity();
        List<AuthorEntity> authorsList = new ArrayList<>();
        authorsList.add(author);
        authorsList.add(author2);
        authorsList.add(author3);
        return ResponseEntity.ok(authorsList);
    }
}
