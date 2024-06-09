package com.company.library.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LateReturnChargeDTO {
    private Long id;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    @DecimalMax(value = "99999999.99", message = "Amount must not exceed 99,999,999.99")
    private BigDecimal amount;

    @NotNull(message = "Due date is required")
    @FutureOrPresent(message = "Due date must be in the present or future")
    private LocalDate dueDate;

    @FutureOrPresent(message = "Payment date must be in the present or future")
    private LocalDate paymentDate;

    @NotNull(message = "Payment status is required")
    private boolean isPaid;

    @NotNull(message = "Borrowing record ID is required")
    private Long borrowingRecordId;

}
