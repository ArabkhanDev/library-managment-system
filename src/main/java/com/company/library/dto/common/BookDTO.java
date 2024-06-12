package com.company.library.dto.common;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @Size(max = 255, message = "Subtitle cannot exceed 255 characters")
    private String subtitle;

    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 13, message = "ISBN must be between 10 and 13 characters")
    private String isbn;

    @NotNull(message = "Publication year is required")
    @Min(value = 1000, message = "Publication year must be a valid year")
    private Integer publicationYear;

    @Size(max = 255, message = "Edition cannot exceed 255 characters")
    private String edition;

    @NotNull(message = "Pages is required")
    @Min(value = 1, message = "Pages must be at least 1")
    private Integer pages;

    @NotBlank(message = "Language is required")
    @Size(max = 100, message = "Language cannot exceed 100 characters")
    @Pattern(regexp = "^[a-zA-Z]+(?:-[a-zA-Z]+)*$", message = "Language must consist of letters and optional hyphens")
    private String language;

    @NotNull(message = "Total copies is required")
    @Min(value = 1, message = "Total copies must be at least 1")
    private Integer copies;

    @NotNull(message = "Available copies is required")
    @Min(value = 0, message = "Available copies cannot be negative")
    @Max(value = 999, message = "Available copies cannot exceed 999")
    private Integer availableCopies;

    @Size(max = 255, message = "Shelf location cannot exceed 255 characters")
    private String shelfLocation;

    @NotNull(message = "Author IDs list is required")
    @Size(min = 1, message = "At least one author must be specified")
    private List<Long> authorIds;

    @NotNull(message = "Publishing house ID is required")
    private Long publishingHouseId;

    @NotNull(message = "Category IDs list is required")
    @Size(min = 1, message = "At least one category must be specified")
    private List<Long> categoryIds;




    //check if available copies is less than or equal to total copies
    @AssertTrue(message = "Available copies cannot be greater than total copies")
    private boolean isAvailableCopiesValid() {
        return this.availableCopies <= this.copies;
    }

}

