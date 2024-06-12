package com.company.library.mapper;

import com.company.library.dto.common.BookDTO;
import com.company.library.model.Author;
import com.company.library.model.Book;
import com.company.library.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "authors", target = "authorIds", qualifiedByName = "authorsToIds")
    @Mapping(source = "publishingHouse.id", target = "publishingHouseId")
    @Mapping(source = "categories", target = "categoryIds", qualifiedByName = "categoriesToIds")
    BookDTO toDTO(Book book);

    @Named("authorsToIds")
    default List<Long> authorsToIds(Set<Author> authors) {
        return authors.stream().map(Author::getId).collect(Collectors.toList());
    }

    @Named("categoriesToIds")
    default List<Long> categoriesToIds(Set<Category> categories) {
        return categories.stream().map(Category::getId).collect(Collectors.toList());
    }
}
