package com.example.book_review_system.repository;

import com.example.book_review_system.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b " +
            "FROM Book b " +
            "WHERE  b.isActive = TRUE ")
    List<Book> getAllBooks();

}
