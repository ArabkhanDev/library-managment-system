package com.company.library.criteria;

import lombok.Data;

import java.util.List;

@Data
public class BookCriteria {
    private String title;
    private String subtitle;
    private String isbn;
    private Integer publicationYear;
    private String edition;
    private Integer pages;
    private String language;
    private Integer copies;
    private Integer availableCopies;
    private String shelfLocation;
    private Long publishingHouseId;
    private List<Long> authorIds;
    private List<Long> categoryIds;
}
