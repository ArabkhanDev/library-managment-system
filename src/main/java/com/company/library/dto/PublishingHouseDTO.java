package com.company.library.dto;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishingHouseDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @NotBlank(message = "Address is required")
    @Size(max = 1000, message = "Address cannot exceed 1000 characters")
    private String address;

    @NotBlank(message = "City is required")
    @Size(max = 255, message = "City cannot exceed 255 characters")
    private String city;

    @NotBlank(message = "Country is required")
    @Size(max = 255, message = "Country cannot exceed 255 characters")
    private String country;

    @Pattern(regexp = "^050\\d{7}$", message = "Phone number must be in the format 0505555555")
    private String contactNumber;

    @Email(message = "Email should be valid")
    private String email;

    @URL(message = "Website should be a valid URL")
    private String website;

    @NotNull(message = "Foundation year is required")
    @Min(value = 1000, message = "Foundation year must be a valid year")
    @Max(value = 9999, message = "Foundation year must be a valid year")
    private Integer foundationYear;

}
