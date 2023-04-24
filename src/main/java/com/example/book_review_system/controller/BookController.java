package com.example.book_review_system.controller;

import com.example.book_review_system.component.HeaderHelper;
import com.example.book_review_system.constant.Message;
import com.example.book_review_system.dto.request.CreateBookReqDTO;
import com.example.book_review_system.dto.request.UpdateBookReqDTO;
import com.example.book_review_system.dto.response.BookResDTO;
import com.example.book_review_system.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/all")
    public ResponseEntity<?> getAllBooks() { return ResponseEntity.ok(bookService.getAllBooks());}

    @GetMapping("/books/{bookId}")
    public ResponseEntity<BookResDTO> getBook(@PathVariable("bookId") Long bookId){
        return ResponseEntity.ok(bookService.getBook(bookId));
    }
    @PostMapping("/books")
    public ResponseEntity<BookResDTO> createBook(@RequestBody CreateBookReqDTO createBookReqDTO){
        return ResponseEntity.ok()
                .headers(HeaderHelper.getHeadersMessage(Message.BOOK_CREATED))
                .body(bookService.createBook(createBookReqDTO));
    }
    @PutMapping("/books/{bookId}")
    public ResponseEntity<BookResDTO> updateBook(@PathVariable("bookId") Long bookId,
                                                 @RequestBody UpdateBookReqDTO updateBookReqDTO){
        return ResponseEntity.ok()
                .headers(HeaderHelper.getHeadersMessage(Message.BOOK_MODIFIED))
                .body(bookService.updateBook(bookId,updateBookReqDTO));
    }
    @DeleteMapping("/books/delete/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable("bookId") Long bookId){
        bookService.deleteBook(bookId);
        return ResponseEntity.ok()
                .headers(HeaderHelper.getHeadersMessage(Message.BOOK_DELETED))
                .build();
    }
}
