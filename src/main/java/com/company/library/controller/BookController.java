package com.company.library.controller;

import com.company.library.criteria.BookCriteria;
import com.company.library.dto.BookDTO;
import com.company.library.model.base.BaseResponse;
import com.company.library.service.inter.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public BaseResponse<Page<BookDTO>> getAllBooks(Pageable pageable) {
        Page<BookDTO> books = bookService.getAllBooks(pageable);
        return BaseResponse.success(books);
    }

    @GetMapping("/{id}")
    public BaseResponse<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        return BaseResponse.success(book);
    }

    @PostMapping("/search")
    public BaseResponse<Page<BookDTO>> searchBooks(@RequestBody(required = false) BookCriteria criteria, Pageable pageable) {
        Page<BookDTO> books = bookService.searchBooks(criteria, pageable);
        return BaseResponse.success(books);
    }

    @PostMapping
    public BaseResponse<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return BaseResponse.created(createdBook);
    }

    @PutMapping("/{id}")
    public BaseResponse<BookDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        return BaseResponse.success(updatedBook);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return BaseResponse.notContent();
    }
}
