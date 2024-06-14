package com.company.library.controller;

import com.company.library.criteria.BookCriteria;
import com.company.library.dto.common.BookDTO;
import com.company.library.service.inter.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        bookDTO = new BookDTO(1L, "Book Title", "Book Subtitle", "1234567890", 2023,
                "First Edition", 300, "English", 10, 5, "A1",
                Collections.singletonList(1L), 1L, Collections.singletonList(1L));
    }

    @Test
    public void testGetAllBooks() {
        Page<BookDTO> page = new PageImpl<>(Collections.singletonList(bookDTO));
        when(bookService.getAllBooks(any(Pageable.class))).thenReturn(page);

        Page<BookDTO> result = bookController.getAllBooks(Pageable.unpaged()).getData();

        assertEquals(page.getContent(), result.getContent());
    }

    @Test
    public void testGetBookById() {
        when(bookService.getBookById(1L)).thenReturn(bookDTO);

        BookDTO result = bookController.getBookById(1L).getData();

        assertEquals(bookDTO, result);
    }

    @Test
    public void testSearchBooks() {
        Page<BookDTO> page = new PageImpl<>(Collections.singletonList(bookDTO));
        when(bookService.searchBooks(any(BookCriteria.class), any(Pageable.class))).thenReturn(page);

        Page<BookDTO> result = bookController.searchBooks(new BookCriteria(), Pageable.unpaged()).getData();

        assertEquals(page.getContent(), result.getContent());
    }

    @Test
    public void testCreateBook() {
        when(bookService.createBook(any(BookDTO.class))).thenReturn(bookDTO);

        BookDTO result = bookController.createBook(bookDTO).getData();

        assertEquals(bookDTO, result);
    }

    @Test
    public void testUpdateBook() {
        when(bookService.updateBook(eq(1L), any(BookDTO.class))).thenReturn(bookDTO);

        BookDTO result = bookController.updateBook(1L, bookDTO).getData();

        assertEquals(bookDTO, result);
    }

    @Test
    public void testDeleteBook() {
        doNothing().when(bookService).deleteBook(1L);

        bookController.deleteBook(1L);

        verify(bookService, times(1)).deleteBook(1L);
    }
}
