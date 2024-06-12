package com.company.library.controller;

import com.company.library.dto.common.AuthorDTO;
import com.company.library.model.base.BaseResponse;
import com.company.library.service.inter.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public BaseResponse<Page<AuthorDTO>> getAllAuthors(Pageable pageable) {
        Page<AuthorDTO> authors = authorService.getAllAuthors(pageable);
        return BaseResponse.success(authors);
    }

    @GetMapping("/{id}")
    public BaseResponse<AuthorDTO> getAuthorById(@PathVariable Long id) {
        AuthorDTO author = authorService.getAuthorById(id);
        return BaseResponse.success(author);
    }

    @PostMapping
    public BaseResponse<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        AuthorDTO createdAuthor = authorService.createAuthor(authorDTO);
        return BaseResponse.created(createdAuthor);
    }

    @PutMapping("/{id}")
    public BaseResponse<AuthorDTO> updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorDTO authorDTO) {
        AuthorDTO updatedAuthor = authorService.updateAuthor(id, authorDTO);
        return BaseResponse.success(updatedAuthor);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return BaseResponse.notContent();
    }
}
