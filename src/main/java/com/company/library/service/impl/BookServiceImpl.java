package com.company.library.service.impl;

import com.company.library.criteria.BookCriteria;
import com.company.library.criteria.BookSpecification;
import com.company.library.dto.common.BookDTO;
import com.company.library.exception.types.ResourceNotFoundException;
import com.company.library.mapper.BookMapper;
import com.company.library.model.Book;
import com.company.library.repository.*;
import com.company.library.service.inter.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublishingHouseRepository publishingHouseRepository;
    private final CategoryRepository categoryRepository;
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final LateReturnChargeRepository lateReturnChargeRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<BookDTO> getAllBooks(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);
        return books.map(BookMapper.INSTANCE::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookById(Long id) {
        return bookRepository.findById(id)
                .map(BookMapper.INSTANCE::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    @Override
    public Page<BookDTO> searchBooks(BookCriteria criteria, Pageable pageable) {
        Page<Book> books = bookRepository.findAll(BookSpecification.buildCriteria(criteria), pageable);
        return books.map(BookMapper.INSTANCE::toDTO);
    }

    @Override
    @Transactional
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = new Book();
        updateBookFromDTO(book, bookDTO);
        Book savedBook = bookRepository.save(book);
        return BookMapper.INSTANCE.toDTO(savedBook);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        updateBookFromDTO(existingBook, bookDTO);
        Book updatedBook = bookRepository.save(existingBook);
        return BookMapper.INSTANCE.toDTO(updatedBook);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        book.getBorrowingRecords().forEach(record -> record.setBook(null));
        book.getBorrowingRecords().clear();

        book.getCategories().clear();

        book.getAuthors().forEach(author -> author.getBooks().remove(book));
        book.getAuthors().clear();

        book.setPublishingHouse(null);

        bookRepository.delete(book);
    }


    // Helper methods
    private void updateBookFromDTO(Book book, BookDTO bookDTO) {
        book.setTitle(bookDTO.getTitle());
        book.setSubtitle(bookDTO.getSubtitle());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublicationYear(bookDTO.getPublicationYear());
        book.setEdition(bookDTO.getEdition());
        book.setPages(bookDTO.getPages());
        book.setLanguage(bookDTO.getLanguage());
        book.setCopies(bookDTO.getCopies());
        book.setAvailableCopies(bookDTO.getAvailableCopies());
        book.setShelfLocation(bookDTO.getShelfLocation());

        book.setAuthors(bookDTO.getAuthorIds().stream()
                .map(id -> authorRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id)))
                .collect(Collectors.toSet()));

        book.setPublishingHouse(publishingHouseRepository.findById(bookDTO.getPublishingHouseId())
                .orElseThrow(() -> new ResourceNotFoundException("PublishingHouse not found with id: " + bookDTO.getPublishingHouseId())));

        book.setCategories(bookDTO.getCategoryIds().stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id)))
                .collect(Collectors.toSet()));
    }
}
