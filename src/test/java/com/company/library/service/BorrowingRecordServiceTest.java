package com.company.library.service;


import com.company.library.dto.common.BorrowingRecordDTO;
import com.company.library.exception.types.ResourceNotFoundException;
import com.company.library.model.Book;
import com.company.library.model.BorrowingRecord;
import com.company.library.model.Member;
import com.company.library.repository.BookRepository;
import com.company.library.repository.BorrowingRecordRepository;
import com.company.library.repository.MemberRepository;
import com.company.library.service.impl.BorrowingRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BorrowingRecordServiceTest {

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private BorrowingRecordServiceImpl borrowingRecordService;

    private BorrowingRecord borrowingRecord;
    private BorrowingRecordDTO borrowingRecordDTO;
    private Book book;
    private Member member;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Sample Book");

        member = new Member();
        member.setId(1L);
        member.setFirstName("Sample Member Name");
        member.setLastName("Sample Member Last Name");

        borrowingRecord = new BorrowingRecord();
        borrowingRecord.setId(1L);
        borrowingRecord.setBorrowDate(LocalDate.now());
        borrowingRecord.setDueDate(LocalDate.now().plusDays(14));
        borrowingRecord.setReturnDate(null);
        borrowingRecord.setReturned(false);
        borrowingRecord.setBook(book);
        borrowingRecord.setMember(member);

        borrowingRecordDTO = new BorrowingRecordDTO();
        borrowingRecordDTO.setId(1L);
        borrowingRecordDTO.setBorrowDate(LocalDate.now());
        borrowingRecordDTO.setDueDate(LocalDate.now().plusDays(14));
        borrowingRecordDTO.setReturnDate(null);
        borrowingRecordDTO.setReturned(false);
        borrowingRecordDTO.setBookId(1L);
        borrowingRecordDTO.setMemberId(1L);
    }

    @Test
    void getAllBorrowingRecords() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<BorrowingRecord> page = new PageImpl<>(List.of(borrowingRecord));

        when(borrowingRecordRepository.findAll(pageable)).thenReturn(page);

        Page<BorrowingRecordDTO> result = borrowingRecordService.getAllBorrowingRecords(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(borrowingRecordDTO.getId(), result.getContent().get(0).getId());
        verify(borrowingRecordRepository, times(1)).findAll(pageable);
    }

    @Test
    void getBorrowingRecordById() {
        when(borrowingRecordRepository.findById(1L)).thenReturn(Optional.of(borrowingRecord));

        BorrowingRecordDTO result = borrowingRecordService.getBorrowingRecordById(1L);

        assertNotNull(result);
        assertEquals(borrowingRecordDTO.getId(), result.getId());
        verify(borrowingRecordRepository, times(1)).findById(1L);
    }

    @Test
    void getBorrowingRecordByIdNotFound() {
        when(borrowingRecordRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            borrowingRecordService.getBorrowingRecordById(1L);
        });

        assertEquals("BorrowingRecord not found with id: 1", exception.getMessage());
    }

    @Test
    void getBorrowingRecordsByMemberId() {
        when(borrowingRecordRepository.findByMemberId(1L)).thenReturn(List.of(borrowingRecord));

        List<BorrowingRecordDTO> result = borrowingRecordService.getBorrowingRecordsByMemberId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(borrowingRecordDTO.getId(), result.get(0).getId());
        verify(borrowingRecordRepository, times(1)).findByMemberId(1L);
    }

    @Test
    void getBorrowingRecordsByBookId() {
        when(borrowingRecordRepository.findByBookId(1L)).thenReturn(List.of(borrowingRecord));

        List<BorrowingRecordDTO> result = borrowingRecordService.getBorrowingRecordsByBookId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(borrowingRecordDTO.getId(), result.get(0).getId());
        verify(borrowingRecordRepository, times(1)).findByBookId(1L);
    }

    @Test
    void createBorrowingRecord() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        BorrowingRecordDTO result = borrowingRecordService.createBorrowingRecord(borrowingRecordDTO);

        assertNotNull(result);
        assertEquals(borrowingRecordDTO.getId(), result.getId());
        verify(borrowingRecordRepository, times(1)).save(any(BorrowingRecord.class));
    }

    @Test
    void updateBorrowingRecord() {
        when(borrowingRecordRepository.findById(1L)).thenReturn(Optional.of(borrowingRecord));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        BorrowingRecordDTO result = borrowingRecordService.updateBorrowingRecord(1L, borrowingRecordDTO);

        assertNotNull(result);
        assertEquals(borrowingRecordDTO.getId(), result.getId());
        verify(borrowingRecordRepository, times(1)).save(any(BorrowingRecord.class));
    }

    @Test
    void updateBorrowingRecordNotFound() {
        when(borrowingRecordRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            borrowingRecordService.updateBorrowingRecord(1L, borrowingRecordDTO);
        });

        assertEquals("BorrowingRecord not found with id: 1", exception.getMessage());
    }

    @Test
    void deleteBorrowingRecord() {
        when(borrowingRecordRepository.findById(1L)).thenReturn(Optional.of(borrowingRecord));

        borrowingRecordService.deleteBorrowingRecord(1L);

        verify(borrowingRecordRepository, times(1)).delete(borrowingRecord);
    }

    @Test
    void deleteBorrowingRecordNotFound() {
        when(borrowingRecordRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            borrowingRecordService.deleteBorrowingRecord(1L);
        });

        assertEquals("BorrowingRecord not found with id: 1", exception.getMessage());
    }
}
