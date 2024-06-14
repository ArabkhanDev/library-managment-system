package com.company.library.model;

import com.company.library.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "publishing_houses")
@AllArgsConstructor
@NoArgsConstructor
public class PublishingHouse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "contact_number", nullable = false)
    private String contactNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "website")
    private String website;

    @Column(name = "foundation_year")
    private Integer foundationYear;
}
