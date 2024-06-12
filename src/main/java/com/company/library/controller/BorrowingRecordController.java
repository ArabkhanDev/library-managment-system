package com.company.library.controller;

import com.company.library.dto.common.BorrowingRecordDTO;
import com.company.library.model.base.BaseResponse;
import com.company.library.service.inter.BorrowingRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/borrowing-records")
public class BorrowingRecordController {

    private final BorrowingRecordService borrowingRecordService;

    @GetMapping
    public BaseResponse<Page<BorrowingRecordDTO>> getAllBorrowingRecords(Pageable pageable) {
        Page<BorrowingRecordDTO> borrowingRecords = borrowingRecordService.getAllBorrowingRecords(pageable);
        return BaseResponse.success(borrowingRecords);
    }

    @GetMapping("/{id}")
    public BaseResponse<BorrowingRecordDTO> getBorrowingRecordById(@PathVariable Long id) {
        BorrowingRecordDTO borrowingRecord = borrowingRecordService.getBorrowingRecordById(id);
        return BaseResponse.success(borrowingRecord);
    }

    @GetMapping("/by-member/{memberId}")
    public BaseResponse<List<BorrowingRecordDTO>> getBorrowingRecordsByMemberId(@PathVariable Long memberId) {
        List<BorrowingRecordDTO> borrowingRecords = borrowingRecordService.getBorrowingRecordsByMemberId(memberId);
        return BaseResponse.success(borrowingRecords);
    }

    @GetMapping("/by-book/{bookId}")
    public BaseResponse<List<BorrowingRecordDTO>> getBorrowingRecordsByBookId(@PathVariable Long bookId) {
        List<BorrowingRecordDTO> borrowingRecords = borrowingRecordService.getBorrowingRecordsByBookId(bookId);
        return BaseResponse.success(borrowingRecords);
    }

    @PostMapping
    public BaseResponse<BorrowingRecordDTO> createBorrowingRecord(@Valid @RequestBody BorrowingRecordDTO borrowingRecordDTO) {
        BorrowingRecordDTO createdBorrowingRecord = borrowingRecordService.createBorrowingRecord(borrowingRecordDTO);
        return BaseResponse.created(createdBorrowingRecord);
    }

    @PutMapping("/{id}")
    public BaseResponse<BorrowingRecordDTO> updateBorrowingRecord(@PathVariable Long id, @Valid @RequestBody BorrowingRecordDTO borrowingRecordDTO) {
        BorrowingRecordDTO updatedBorrowingRecord = borrowingRecordService.updateBorrowingRecord(id, borrowingRecordDTO);
        return BaseResponse.success(updatedBorrowingRecord);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteBorrowingRecord(@PathVariable Long id) {
        borrowingRecordService.deleteBorrowingRecord(id);
        return BaseResponse.notContent();
    }
}