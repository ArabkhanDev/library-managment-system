package com.company.library.controller;

import com.company.library.dto.common.BorrowingRecordDTO;
import com.company.library.model.BorrowingRecord;
import com.company.library.service.inter.BorrowingRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BorrowingRecordControllerTest {

    @Mock
    private BorrowingRecordService borrowingRecordService;

    @InjectMocks
    private BorrowingRecordController borrowingRecordController;

    private BorrowingRecordDTO borrowingRecordDTO;
    private BorrowingRecord borrowingRecord;

    @BeforeEach
    void setUp() {
        borrowingRecordDTO = new BorrowingRecordDTO(1L, LocalDate.now(), LocalDate.now().plusDays(14),
                null, false, 1L, 1L);
        borrowingRecord = new BorrowingRecord(1L, LocalDate.now(), LocalDate.now().plusDays(14),
                null, false, null, null, null);
    }

    @Test
    public void testGetAllBorrowingRecords() {
        Page<BorrowingRecordDTO> page = new PageImpl<>(Collections.singletonList(borrowingRecordDTO));
        when(borrowingRecordService.getAllBorrowingRecords(any(Pageable.class))).thenReturn(page);

        Page<BorrowingRecordDTO> result = borrowingRecordController.getAllBorrowingRecords(Pageable.unpaged()).getData();

        assertEquals(page.getContent(), result.getContent());
    }

    @Test
    public void testGetBorrowingRecordById() {
        when(borrowingRecordService.getBorrowingRecordById(1L)).thenReturn(borrowingRecordDTO);

        BorrowingRecordDTO result = borrowingRecordController.getBorrowingRecordById(1L).getData();

        assertEquals(borrowingRecordDTO, result);
    }

    @Test
    public void testGetBorrowingRecordsByMemberId() {
        List<BorrowingRecordDTO> records = Collections.singletonList(borrowingRecordDTO);
        when(borrowingRecordService.getBorrowingRecordsByMemberId(1L)).thenReturn(records);

        List<BorrowingRecordDTO> result = borrowingRecordController.getBorrowingRecordsByMemberId(1L).getData();

        assertEquals(records, result);
    }

    @Test
    public void testGetBorrowingRecordsByBookId() {
        List<BorrowingRecordDTO> records = Collections.singletonList(borrowingRecordDTO);
        when(borrowingRecordService.getBorrowingRecordsByBookId(1L)).thenReturn(records);

        List<BorrowingRecordDTO> result = borrowingRecordController.getBorrowingRecordsByBookId(1L).getData();

        assertEquals(records, result);
    }

    @Test
    public void testCreateBorrowingRecord() {
        when(borrowingRecordService.createBorrowingRecord(any(BorrowingRecordDTO.class))).thenReturn(borrowingRecordDTO);

        BorrowingRecordDTO result = borrowingRecordController.createBorrowingRecord(borrowingRecordDTO).getData();

        assertEquals(borrowingRecordDTO, result);
    }

    @Test
    public void testUpdateBorrowingRecord() {
        when(borrowingRecordService.updateBorrowingRecord(eq(1L), any(BorrowingRecordDTO.class))).thenReturn(borrowingRecordDTO);

        BorrowingRecordDTO result = borrowingRecordController.updateBorrowingRecord(1L, borrowingRecordDTO).getData();

        assertEquals(borrowingRecordDTO, result);
    }

    @Test
    public void testDeleteBorrowingRecord() {
        doNothing().when(borrowingRecordService).deleteBorrowingRecord(1L);

        borrowingRecordController.deleteBorrowingRecord(1L);

        verify(borrowingRecordService, times(1)).deleteBorrowingRecord(1L);
    }
}
