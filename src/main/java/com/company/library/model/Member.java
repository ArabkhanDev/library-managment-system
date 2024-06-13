package com.company.library.model;

import com.company.library.enums.MembershipType;
import com.company.library.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "members")
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "join_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "membership_type", nullable = false)
    private MembershipType membershipType;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowingRecord> borrowingRecords;
}
