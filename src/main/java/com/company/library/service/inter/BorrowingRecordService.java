package com.company.library.service.inter;

import com.company.library.dto.BorrowingRecordDTO;

import java.util.List;

public interface BorrowingRecordService {

    List<BorrowingRecordDTO> getAllBorrowingRecords();

    BorrowingRecordDTO getBorrowingRecordById(Long id);

    List<BorrowingRecordDTO> getBorrowingRecordsByMemberId(Long memberId);

    List<BorrowingRecordDTO> getBorrowingRecordsByBookId(Long bookId);

    BorrowingRecordDTO createBorrowingRecord(BorrowingRecordDTO borrowingRecordDTO);

    BorrowingRecordDTO updateBorrowingRecord(Long id, BorrowingRecordDTO borrowingRecordDTO);

    void deleteBorrowingRecord(Long id);

}
