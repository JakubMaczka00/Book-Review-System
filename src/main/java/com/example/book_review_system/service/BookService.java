package com.example.book_review_system.service;

import com.example.book_review_system.dto.BookResDTO;
import com.example.book_review_system.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public List<BookResDTO> getAllBooks(){
        return bookRepository.getAllBooks()
                                        .stream()
                                        .map(BookResDTO::new)
                                        .collect(Collectors.toList());
    }
}
