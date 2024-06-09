package com.company.library.repository;

import com.company.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByPublishingHouseId(Long publishingHouseId);
}
