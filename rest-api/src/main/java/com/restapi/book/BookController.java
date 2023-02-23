package com.restapi.book;

import com.restapi.book.model.entity.BookEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @GetMapping
    public ResponseEntity<List<BookEntity>> getAll(){
        BookEntity book = new BookEntity();
        BookEntity book2 = new BookEntity();
        BookEntity book3 = new BookEntity();
        List<BookEntity> books = new ArrayList<>();
        books.add(book);
        books.add(book2);
        books.add(book3);
        return ResponseEntity.ok(books);
    }
}
