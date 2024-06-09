package com.company.library.repository;

import com.company.library.model.LateReturnCharge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LateReturnChargeRepository extends JpaRepository<LateReturnCharge, Long> {
    List<LateReturnCharge> findByBorrowingRecordId(Long borrowingRecordId);
    List<LateReturnCharge> findByIsPaid(boolean isPaid);
}
