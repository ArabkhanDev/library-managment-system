package com.company.library.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingRecordDTO {

    private Long id;

    @NotNull(message = "Borrow date is required")
    @PastOrPresent(message = "Borrow date must be in the past or present")
    private LocalDate borrowDate;

    @NotNull(message = "Due date is required")
    @FutureOrPresent(message = "Due date must be in the present or future")
    private LocalDate dueDate;

    @PastOrPresent(message = "Return date must be in the past or present")
    private LocalDate returnDate;

    @NotNull(message = "Return status is required")
    private boolean isReturned;

    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotNull(message = "Member ID is required")
    private Long memberId;

}

