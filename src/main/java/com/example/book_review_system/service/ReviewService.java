package com.example.book_review_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.book_review_system.repository.BookRepository;
import com.example.book_review_system.repository.ReviewRepository;
import com.example.book_review_system.repository.UserRepository;
import com.google.common.base.Preconditions;
import com.example.book_review_system.component.Role;
import com.example.book_review_system.constant.Message;
import com.example.book_review_system.dto.request.CreateReviewReqDTO;
import com.example.book_review_system.dto.request.UpdateReviewReqDTO;
import com.example.book_review_system.dto.response.ReviewResDTO;
import com.example.book_review_system.entity.Book;
import com.example.book_review_system.entity.Review;
import com.example.book_review_system.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<ReviewResDTO> getReviewsByBookId(Long bookId) {
        return reviewRepository.findByBookId(bookId)
                                                .stream()
                                                .map(ReviewResDTO::new)
                                                .collect(Collectors.toList());
    }

    public ReviewResDTO createReview(CreateReviewReqDTO api) {
        User user = getAuthenticatedUser();
        Book book = bookRepository.getBookById(api.getBookId());
        Preconditions.checkArgument(book != null, Message.BOOK_DOES_NOT_EXIST);
        checkIfUserReviewedBook(api.getBookId(), user);
        Review review = Review.builder()
                    .rating(api.getRating())
                    .title(api.getTitle())
                    .comment(api.getComment())
                    .book(book)
                    .user(user)
                    .build();
        return new ReviewResDTO(reviewRepository.save(review));
    }

    public ReviewResDTO updateReview(Long reviewId, UpdateReviewReqDTO api) {
        Review review = reviewRepository.getOne(reviewId);
        Preconditions.checkArgument(review != null, Message.REVIEW_DOES_NOT_EXIST);
        User user = getAuthenticatedUser();
        // this if statement checks whether the user who issued the request is in fact the author
        // of the given review
        if (review.getUser().getId() == user.getId()) {
            review.setRating(api.getRating());
            review.setTitle(api.getTitle());
            review.setComment(api.getComment());
            return new ReviewResDTO(reviewRepository.save(review));
        } else {
            throw new AccessDeniedException(Message.REVIEW_NOT_AUTHOR);
        }
    }

    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.getOne(reviewId);
        Preconditions.checkArgument(review != null, Message.REVIEW_DOES_NOT_EXIST);
        User user = getAuthenticatedUser();
        if (user.getId() == review.getUser().getId() || user.getRole().equals(Role.ADMIN)) {
            reviewRepository.delete(review);
        } else {
            throw new AccessDeniedException(Message.REVIEW_NOT_AUTHOR);
        }
    }

    // checks whether the user has already reviewed the given book (a single user can only post one review
    // for the given book)
    public void checkIfUserReviewedBook(Long bookId, User user) {
        Review review = null;
        Integer userId = user.getId();
        review = reviewRepository.findByBookIdAndUserId(bookId, userId);
        Preconditions.checkArgument(review == null, Message.REVIEW_EXISTS);
    }


    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        return user;
    }
}
