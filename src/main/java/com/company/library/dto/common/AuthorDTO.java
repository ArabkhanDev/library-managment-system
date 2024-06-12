package com.company.library.dto.common;

import com.company.library.model.Author;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorDTO {
    private Long id;

    @NotBlank(message = "First name is mandatory")
    @Size(max = 100, message = "First name cannot be longer than 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(max = 100, message = "Last name cannot be longer than 100 characters")
    private String lastName;

    @Past(message = "Birth date must be a past date")
    private LocalDate birthDate;

    @Pattern(regexp = "^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$", message = "Nationality is not valid")
    private String nationality;

    @Size(max = 2000, message = "Biography cannot be longer than 2000 characters")
    private String biography;

    @Size(max = 200, message = "Website URL cannot be longer than 200 characters")
    private String website;


    public AuthorDTO(Author author) {
        this.id = author.getId();
        this.firstName = author.getFirstName();
        this.lastName = author.getLastName();
        this.birthDate = author.getBirthDate();
        this.nationality = author.getNationality();
        this.biography = author.getBiography();
        this.website = author.getWebsite();
    }

}
