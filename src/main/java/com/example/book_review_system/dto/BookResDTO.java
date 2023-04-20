package com.example.book_review_system.dto;

import com.example.book_review_system.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResDTO {
    private Integer bookId;
    private String title;
    private String author;
    private Boolean isActive;
    public BookResDTO(Book book){
        this.bookId = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.isActive = book.getIsActive();

    }
}
