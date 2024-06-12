package com.company.library.dto.common;

import com.company.library.model.Member;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberDTO {

    private Long id;

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 50, message = "First name cannot be longer than 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 50, message = "Last name cannot be longer than 50 characters")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^050\\d{7}$", message = "Phone number must be in the format 0505555555")
    private String phoneNumber;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 100, message = "Address cannot be longer than 100 characters")
    private String address;

    @NotNull(message = "Join date cannot be null")
    @PastOrPresent(message = "Join date cannot be in the future")
    private LocalDate joinDate;

    @NotBlank(message = "Membership type cannot be blank")
    private String membershipType;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;

    public MemberDTO(Member member) {
        this.id = member.getId();
        this.firstName = member.getFirstName();
        this.lastName = member.getLastName();
        this.email = member.getEmail();
        this.phoneNumber = member.getPhoneNumber();
        this.address = member.getAddress();
        this.joinDate = member.getJoinDate();
        this.membershipType = member.getMembershipType().name();
        this.isActive = member.getIsActive();
    }
}
