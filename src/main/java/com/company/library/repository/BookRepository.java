package com.company.library.repository;

import com.company.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByPublishingHouseId(Long publishingHouseId);

    Page<Book> findAll(Specification<Book> bookSpecification, Pageable pageable);
}
