package com.example.book_review_system.service;

import com.example.book_review_system.constant.Message;
import com.example.book_review_system.dto.request.CreateBookReqDTO;
import com.example.book_review_system.dto.request.UpdateBookReqDTO;
import com.example.book_review_system.dto.response.BookResDTO;
import com.example.book_review_system.entity.Book;
import com.example.book_review_system.repository.BookRepository;
import com.google.common.base.Preconditions;
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
    public BookResDTO getBook(Long bookId){
        Book book = bookRepository.getOne(bookId);
        return BookResDTO.builder()
                .bookId(book.getId())
                .author(book.getAuthor())
                .title(book.getTitle())
                .isActive(book.getIsActive())
                .build();
    }
    public BookResDTO createBook(CreateBookReqDTO api){
        checkIfBookExists(api.getTitle());
        Book book = Book.builder()
                .author(api.getAuthor())
                .title(api.getTitle())
                .isActive(Boolean.TRUE)
                .build();
        return new BookResDTO(bookRepository.save(book));

    }
    public BookResDTO updateBook(Long bookId, UpdateBookReqDTO api){
        Book book = bookRepository.getOne(bookId);
        checkIfBookExists(api.getTitle());
        book.setAuthor(api.getAuthor());
        book.setTitle(api.getTitle());
        book.setIsActive(api.getIsActive());

        return new BookResDTO(bookRepository.save(book));
    }

    public void deleteBook(Long bookId){
        Book book = bookRepository.getOne(bookId);
        Preconditions.checkArgument(book.getIsActive(), Message.BOOK_CANNOT_BE_DELETED);
        book.setIsActive(false);
        bookRepository.save(book);
    }

    private void checkIfBookExists(String title){
        boolean isExist = bookRepository.existByTitle(title);
        Preconditions.checkArgument(!isExist, Message.BOOK_EXISTS);
    }
}
