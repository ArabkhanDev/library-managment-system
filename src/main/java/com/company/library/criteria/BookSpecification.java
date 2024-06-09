package com.company.library.criteria;

import com.company.library.model.Book;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BookSpecification {

    public static Specification<Book> buildCriteria(BookCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(criteria.getTitle())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + criteria.getTitle().toLowerCase() + "%"));
            }

            if (StringUtils.hasText(criteria.getSubtitle())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("subtitle")), "%" + criteria.getSubtitle().toLowerCase() + "%"));
            }

            if (StringUtils.hasText(criteria.getIsbn())) {
                predicates.add(criteriaBuilder.equal(root.get("isbn"), criteria.getIsbn()));
            }

            if (criteria.getPublicationYear() != null) {
                predicates.add(criteriaBuilder.equal(root.get("publicationYear"), criteria.getPublicationYear()));
            }

            if (StringUtils.hasText(criteria.getEdition())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("edition")), "%" + criteria.getEdition().toLowerCase() + "%"));
            }

            if (criteria.getPages() != null) {
                predicates.add(criteriaBuilder.equal(root.get("pages"), criteria.getPages()));
            }

            if (StringUtils.hasText(criteria.getLanguage())) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("language")), criteria.getLanguage().toLowerCase()));
            }

            if (criteria.getCopies() != null) {
                predicates.add(criteriaBuilder.equal(root.get("copies"), criteria.getCopies()));
            }

            if (criteria.getAvailableCopies() != null) {
                predicates.add(criteriaBuilder.equal(root.get("availableCopies"), criteria.getAvailableCopies()));
            }

            if (StringUtils.hasText(criteria.getShelfLocation())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("shelfLocation")), "%" + criteria.getShelfLocation().toLowerCase() + "%"));
            }

            if (criteria.getPublishingHouseId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("publishingHouse").get("id"), criteria.getPublishingHouseId()));
            }

            if (criteria.getAuthorIds() != null && !criteria.getAuthorIds().isEmpty()) {
                Join<Object, Object> authorJoin = root.join("authors");
                predicates.add(authorJoin.get("id").in(criteria.getAuthorIds()));
            }

            if (criteria.getCategoryIds() != null && !criteria.getCategoryIds().isEmpty()) {
                Join<Object, Object> categoryJoin = root.join("categories");
                predicates.add(categoryJoin.get("id").in(criteria.getCategoryIds()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
