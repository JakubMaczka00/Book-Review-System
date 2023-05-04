package com.example.book_review_system.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewReqDTO {
    private Short rating;
    private String title;
    private String comment;
    private Long bookId;
}
