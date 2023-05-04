package com.example.book_review_system.repository;

import com.example.book_review_system.entity.Review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBookId(Long bookId);
    Review findByBookIdAndUserId(Long bookId, Integer userId);
}
