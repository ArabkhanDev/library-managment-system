package com.company.library.controller;

import com.company.library.dto.common.AuthorDTO;
import com.company.library.model.Author;
import com.company.library.service.inter.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private AuthorDTO authorDTO;

    @BeforeEach
    void setUp() {
        authorDTO = new AuthorDTO(1L, "John", "Doe", LocalDate.of(1980, 1, 1), "American", "Biography", "http://johnswebsite.com");
        Author author = new Author(1L, "John", "Doe", LocalDate.of(1980, 1, 1), "American", "Biography", "http://johnswebsite.com", null);
    }

    @Test
    public void testGetAllAuthors() {
        Page<AuthorDTO> page = new PageImpl<>(Collections.singletonList(authorDTO));
        when(authorService.getAllAuthors(any(Pageable.class))).thenReturn(page);

        Page<AuthorDTO> result = authorController.getAllAuthors(Pageable.unpaged()).getData();

        assertEquals(page.getContent(), result.getContent());
    }

    @Test
    public void testGetAuthorById() {
        when(authorService.getAuthorById(1L)).thenReturn(authorDTO);

        AuthorDTO result = authorController.getAuthorById(1L).getData();

        assertEquals(authorDTO, result);
    }

    @Test
    public void testCreateAuthor() {
        when(authorService.createAuthor(any(AuthorDTO.class))).thenReturn(authorDTO);

        AuthorDTO result = authorController.createAuthor(authorDTO).getData();

        assertEquals(authorDTO, result);
    }

    @Test
    public void testUpdateAuthor() {
        when(authorService.updateAuthor(eq(1L), any(AuthorDTO.class))).thenReturn(authorDTO);

        AuthorDTO result = authorController.updateAuthor(1L, authorDTO).getData();

        assertEquals(authorDTO, result);
    }

    @Test
    public void testDeleteAuthor() {
        doNothing().when(authorService).deleteAuthor(1L);

        authorController.deleteAuthor(1L);

        verify(authorService, times(1)).deleteAuthor(1L);
    }
}
