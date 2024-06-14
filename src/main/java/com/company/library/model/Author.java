package com.company.library.model;

import com.company.library.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "authors")
@AllArgsConstructor
@NoArgsConstructor
public class Author extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Column(name = "biography", columnDefinition = "TEXT")
    private String biography;

    @Column(name = "website")
    private String website;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();
}
