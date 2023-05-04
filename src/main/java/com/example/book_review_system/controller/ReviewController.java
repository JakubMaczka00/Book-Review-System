package com.example.book_review_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.book_review_system.component.HeaderHelper;
import com.example.book_review_system.constant.Message;
import com.example.book_review_system.dto.request.CreateReviewReqDTO;
import com.example.book_review_system.dto.request.UpdateReviewReqDTO;
import com.example.book_review_system.dto.response.ReviewResDTO;
import com.example.book_review_system.service.ReviewService;

@RestController
@RequestMapping("/api")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // get all reviews for the given book
    @GetMapping("/reviews/{bookId}")
    public ResponseEntity<List<ReviewResDTO>> getBookReviews(@PathVariable("bookId") Long bookId) {
        return ResponseEntity.ok(reviewService.getReviewsByBookId(bookId));
    }

    @PostMapping("/reviews")
    public ResponseEntity<ReviewResDTO> createReview(@RequestBody CreateReviewReqDTO createReviewReqDTO){
        try {
            return ResponseEntity.ok()
                    .headers(HeaderHelper.getHeadersMessage(Message.REVIEW_CREATED))
                    .body(reviewService.createReview(createReviewReqDTO));
        } catch(Exception ex) {
            return ResponseEntity.badRequest()
                .headers(HeaderHelper.getHeadersMessage(ex.getMessage()))
                .body(null);
        }
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResDTO> updateReview(@PathVariable("reviewId") Long reviewId,
                                                    @RequestBody UpdateReviewReqDTO updateReviewReqDTO) {
        try {
            return ResponseEntity.ok()
                .headers(HeaderHelper.getHeadersMessage(Message.REVIEW_MODIFIED))
                .body(reviewService.updateReview(reviewId, updateReviewReqDTO));
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                .headers(HeaderHelper.getHeadersMessage(ex.getMessage()))
                .body(null);
        }
    }

    @DeleteMapping("/reviews/delete/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable("reviewId") Long reviewId) {
        try{
            reviewService.deleteReview(reviewId);
            return ResponseEntity.ok()
                .headers(HeaderHelper.getHeadersMessage(Message.REVIEW_DELETED))
                .build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                .headers(HeaderHelper.getHeadersMessage(ex.getMessage()))
                .body(null);
            }
    }
}
