package com.company.library.model;

import com.company.library.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "late_return_charges")
@AllArgsConstructor
@NoArgsConstructor
public class LateReturnCharge extends BaseEntity {

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "due_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @Column(name = "payment_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

    @Column(name = "is_paid", nullable = false)
    private boolean isPaid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "borrowing_record_id", nullable = false, unique = true)
    private BorrowingRecord borrowingRecord;

}
