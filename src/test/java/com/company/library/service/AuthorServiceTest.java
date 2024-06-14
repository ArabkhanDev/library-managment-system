package com.company.library.service;

import com.company.library.dto.common.AuthorDTO;
import com.company.library.exception.types.ResourceNotFoundException;
import com.company.library.mapper.AuthorMapper;
import com.company.library.model.Author;
import com.company.library.repository.AuthorRepository;
import com.company.library.service.impl.AuthorServiceImpl;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author author;
    private AuthorDTO authorDTO;

    @BeforeEach
    void setUp() {
        author = new Author(1L, "John", "Doe", LocalDate.of(1980, 5, 15), "American", "Bio", "www.example.com", null);
        authorDTO = AuthorMapper.INSTANCE.toDTO(author);
    }

    @Test
    void testGetAllAuthors() {
        List<Author> authors = Arrays.asList(author, new Author(1L, "Jane", "Smith", LocalDate.of(1985, 9, 20), "English", "Bio", "www.example.com", null));
        Pageable pageable = PageRequest.of(0, 10);
        Page<Author> authorPage = new PageImpl<>(authors, pageable, authors.size());

        when(authorRepository.findAll(pageable)).thenReturn(authorPage);

        Page<AuthorDTO> result = authorService.getAllAuthors(pageable);

        assertEquals(authors.size(), result.getContent().size());
        verify(authorRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetAuthorById() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        AuthorDTO result = authorService.getAuthorById(1L);

        assertEquals(authorDTO, result);
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAuthorById_NotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authorService.getAuthorById(1L));
        verify(authorRepository, times(1)).findById(1L);
    }

}