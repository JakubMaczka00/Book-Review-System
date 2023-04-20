package com.example.book_review_system.controller;

import com.example.book_review_system.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping("/book/all")
    public ResponseEntity<?> getAllBooks() { return ResponseEntity.ok(bookService.getAllBooks());}
}
