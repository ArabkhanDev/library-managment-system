package com.company.library.service.inter;

import com.company.library.dto.common.BorrowingRecordDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BorrowingRecordService {

    Page<BorrowingRecordDTO> getAllBorrowingRecords(Pageable pageable);

    BorrowingRecordDTO getBorrowingRecordById(Long id);

    List<BorrowingRecordDTO> getBorrowingRecordsByMemberId(Long memberId);

    List<BorrowingRecordDTO> getBorrowingRecordsByBookId(Long bookId);

    BorrowingRecordDTO createBorrowingRecord(BorrowingRecordDTO borrowingRecordDTO);

    BorrowingRecordDTO updateBorrowingRecord(Long id, BorrowingRecordDTO borrowingRecordDTO);

    void deleteBorrowingRecord(Long id);

}
