package com.company.library.controller;

import com.company.library.dto.BorrowingRecordDTO;
import com.company.library.service.inter.BorrowingRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/borrowing-records")
public class BorrowingRecordController {

    private final BorrowingRecordService borrowingRecordService;

    @GetMapping
    public ResponseEntity<List<BorrowingRecordDTO>> getAllBorrowingRecords() {
        List<BorrowingRecordDTO> borrowingRecords = borrowingRecordService.getAllBorrowingRecords();
        return ResponseEntity.ok(borrowingRecords);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowingRecordDTO> getBorrowingRecordById(@PathVariable Long id) {
        BorrowingRecordDTO borrowingRecord = borrowingRecordService.getBorrowingRecordById(id);
        return ResponseEntity.ok(borrowingRecord);
    }

    @GetMapping("/by-member/{memberId}")
    public ResponseEntity<List<BorrowingRecordDTO>> getBorrowingRecordsByMemberId(@PathVariable Long memberId) {
        List<BorrowingRecordDTO> borrowingRecords = borrowingRecordService.getBorrowingRecordsByMemberId(memberId);
        return ResponseEntity.ok(borrowingRecords);
    }

    @GetMapping("/by-book/{bookId}")
    public ResponseEntity<List<BorrowingRecordDTO>> getBorrowingRecordsByBookId(@PathVariable Long bookId) {
        List<BorrowingRecordDTO> borrowingRecords = borrowingRecordService.getBorrowingRecordsByBookId(bookId);
        return ResponseEntity.ok(borrowingRecords);
    }

    @PostMapping
    public ResponseEntity<BorrowingRecordDTO> createBorrowingRecord(@Valid @RequestBody BorrowingRecordDTO borrowingRecordDTO) {
        BorrowingRecordDTO createdBorrowingRecord = borrowingRecordService.createBorrowingRecord(borrowingRecordDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBorrowingRecord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BorrowingRecordDTO> updateBorrowingRecord(@PathVariable Long id, @Valid @RequestBody BorrowingRecordDTO borrowingRecordDTO) {
        BorrowingRecordDTO updatedBorrowingRecord = borrowingRecordService.updateBorrowingRecord(id, borrowingRecordDTO);
        return ResponseEntity.ok(updatedBorrowingRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowingRecord(@PathVariable Long id) {
        borrowingRecordService.deleteBorrowingRecord(id);
        return ResponseEntity.noContent().build();
    }
}