package com.company.library.service.inter;

import com.company.library.dto.AuthorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {

    Page<AuthorDTO> getAllAuthors(Pageable pageable);

    AuthorDTO getAuthorById(Long id);

    AuthorDTO createAuthor(AuthorDTO authorDTO);

    AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO);

    void deleteAuthor(Long id);


}
