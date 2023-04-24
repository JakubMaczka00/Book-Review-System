package com.example.book_review_system.repository;

import com.example.book_review_system.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b " +
            "FROM Book b " +
            "WHERE  b.isActive = TRUE ")
    List<Book> getAllBooks();

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Book b " +
            "WHERE b.title = :title AND b.isActive = TRUE ")
    boolean existByTitle(@Param("title") String title);

}
