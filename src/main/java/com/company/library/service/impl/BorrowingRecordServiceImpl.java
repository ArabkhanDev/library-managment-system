package com.company.library.service.impl;

import com.company.library.dto.common.BorrowingRecordDTO;
import com.company.library.exception.types.ResourceNotFoundException;
import com.company.library.mapper.BorrowingRecordMapper;
import com.company.library.model.BorrowingRecord;
import com.company.library.repository.BookRepository;
import com.company.library.repository.BorrowingRecordRepository;
import com.company.library.repository.MemberRepository;
import com.company.library.service.inter.BorrowingRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Override
    public Page<BorrowingRecordDTO> getAllBorrowingRecords(Pageable pageable) {
        Page<BorrowingRecord> borrowingRecords = borrowingRecordRepository.findAll(pageable);
        return borrowingRecords.map(BorrowingRecordMapper.INSTANCE::toDTO);
    }

    @Override
    public BorrowingRecordDTO getBorrowingRecordById(Long id) {
        return borrowingRecordRepository.findById(id)
                .map(BorrowingRecordMapper.INSTANCE::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("BorrowingRecord not found with id: " + id));
    }

    @Override
    public List<BorrowingRecordDTO> getBorrowingRecordsByMemberId(Long memberId) {
        return borrowingRecordRepository.findByMemberId(memberId).stream()
                .map(BorrowingRecordMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowingRecordDTO> getBorrowingRecordsByBookId(Long bookId) {
        return borrowingRecordRepository.findByBookId(bookId).stream()
                .map(BorrowingRecordMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BorrowingRecordDTO createBorrowingRecord(BorrowingRecordDTO borrowingRecordDTO) {
        BorrowingRecord borrowingRecord = BorrowingRecordMapper.INSTANCE.toEntity(borrowingRecordDTO);
        updateBorrowingRecordFromDTO(borrowingRecord, borrowingRecordDTO);
        BorrowingRecord savedBorrowingRecord = borrowingRecordRepository.save(borrowingRecord);
        return BorrowingRecordMapper.INSTANCE.toDTO(savedBorrowingRecord);
    }

    @Override
    public BorrowingRecordDTO updateBorrowingRecord(Long id, BorrowingRecordDTO borrowingRecordDTO) {
        BorrowingRecord existingBorrowingRecord = borrowingRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BorrowingRecord not found with id: " + id));
        updateBorrowingRecordFromDTO(existingBorrowingRecord, borrowingRecordDTO);
        BorrowingRecord updatedBorrowingRecord = borrowingRecordRepository.save(existingBorrowingRecord);
        return BorrowingRecordMapper.INSTANCE.toDTO(updatedBorrowingRecord);
    }

    @Override
    public void deleteBorrowingRecord(Long id) {
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BorrowingRecord not found with id: " + id));
        borrowingRecordRepository.delete(borrowingRecord);
    }



    private void updateBorrowingRecordFromDTO(BorrowingRecord borrowingRecord, BorrowingRecordDTO borrowingRecordDTO) {
        borrowingRecord.setBorrowDate(borrowingRecordDTO.getBorrowDate());
        borrowingRecord.setDueDate(borrowingRecordDTO.getDueDate());
        borrowingRecord.setReturnDate(borrowingRecordDTO.getReturnDate());
        borrowingRecord.setReturned(borrowingRecordDTO.isReturned());

        borrowingRecord.setBook(bookRepository.findById(borrowingRecordDTO.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + borrowingRecordDTO.getBookId())));

        borrowingRecord.setMember(memberRepository.findById(borrowingRecordDTO.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + borrowingRecordDTO.getMemberId())));
    }
}