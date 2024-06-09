package com.company.library.repository;

import com.company.library.model.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    List<BorrowingRecord> findByMemberId(Long memberId);

    List<BorrowingRecord> findByBookId(Long bookId);

}
