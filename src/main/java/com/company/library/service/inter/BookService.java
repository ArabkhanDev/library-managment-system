package com.company.library.service.inter;

import com.company.library.criteria.BookCriteria;
import com.company.library.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface BookService {


    Page<BookDTO> getAllBooks(Pageable pageable);

    BookDTO getBookById(Long id);

    Page<BookDTO> searchBooks(BookCriteria criteria, Pageable pageable);

    BookDTO createBook(BookDTO bookDTO);

    BookDTO updateBook(Long id, BookDTO bookDTO);

    void deleteBook(Long id);





}
