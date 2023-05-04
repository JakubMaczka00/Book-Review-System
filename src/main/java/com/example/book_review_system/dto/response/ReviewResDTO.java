package com.example.book_review_system.dto.response;

import com.example.book_review_system.entity.Review;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResDTO {
    private Long reviewId;
    private Short rating;
    private String title;
    private String comment;

    public ReviewResDTO(Review review) {
        this.reviewId = review.getId();
        this.rating = review.getRating();
        this.title = review.getTitle();
        this.comment = review.getComment();
    }
}
