package com.company.library.service;

import com.company.library.dto.common.BookDTO;
import com.company.library.exception.types.ResourceNotFoundException;
import com.company.library.model.*;
import com.company.library.repository.AuthorRepository;
import com.company.library.repository.BookRepository;
import com.company.library.repository.CategoryRepository;
import com.company.library.repository.PublishingHouseRepository;
import com.company.library.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PublishingHouseRepository publishingHouseRepository;

    private Book book;
    private BookDTO bookDTO;
    private Author author;
    private Category category;
    private PublishingHouse publishingHouse;

    @BeforeEach
    public void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setBorrowingRecords(new ArrayList<>()); // Initialize borrowingRecords

        author = new Author();
        author.setId(1L);

        category = new Category();
        category.setId(1L);

        publishingHouse = new PublishingHouse();
        publishingHouse.setId(1L);

        bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Test Book");
        bookDTO.setAuthorIds(Collections.singletonList(1L));
        bookDTO.setCategoryIds(Collections.singletonList(1L));
        bookDTO.setPublishingHouseId(1L);
    }

    @Test
    public void testGetAllBooks() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(book));

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);

        Page<BookDTO> result = bookService.getAllBooks(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(bookRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testGetBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookDTO result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetBookById_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(1L));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateBook() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(publishingHouseRepository.findById(1L)).thenReturn(Optional.of(publishingHouse));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO result = bookService.createBook(bookDTO);

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testUpdateBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(publishingHouseRepository.findById(1L)).thenReturn(Optional.of(publishingHouse));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO result = bookService.updateBook(1L, bookDTO);

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testUpdateBook_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.updateBook(1L, bookDTO));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setBorrowingRecords(new ArrayList<>());

        Author author = new Author();
        author.setId(1L);
        author.getBooks().add(book);
        book.getAuthors().add(author);

        Category category = new Category();
        category.setId(1L);
        book.getCategories().add(category);

        PublishingHouse publishingHouse = new PublishingHouse();
        publishingHouse.setId(1L);
        book.setPublishingHouse(publishingHouse);

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        book.getBorrowingRecords().add(borrowingRecord); // Add borrowingRecord

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).delete(book);

        assertTrue(book.getAuthors().isEmpty());
        assertTrue(book.getCategories().isEmpty());
        assertTrue(book.getBorrowingRecords().stream().allMatch(record -> record.getBook() == null));
        assertNull(book.getPublishingHouse());
    }

    @Test
    public void testDeleteBook_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook(1L));
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, never()).delete(any());
    }
}
