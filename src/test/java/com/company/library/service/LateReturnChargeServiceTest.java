package com.company.library.service;

import com.company.library.dto.common.LateReturnChargeDTO;
import com.company.library.exception.types.ResourceNotFoundException;
import com.company.library.model.BorrowingRecord;
import com.company.library.model.LateReturnCharge;
import com.company.library.repository.BorrowingRecordRepository;
import com.company.library.repository.LateReturnChargeRepository;
import com.company.library.service.impl.LateReturnChargeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LateReturnChargeServiceTest {

    @Mock
    private LateReturnChargeRepository lateReturnChargeRepository;

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @InjectMocks
    private LateReturnChargeServiceImpl lateReturnChargeService;

    private LateReturnChargeDTO sampleChargeDTO;
    private LateReturnCharge sampleCharge;
    private BorrowingRecord sampleBorrowingRecord;

    @BeforeEach
    public void setup() {
        sampleBorrowingRecord = new BorrowingRecord();
        sampleBorrowingRecord.setId(1L);

        sampleChargeDTO = new LateReturnChargeDTO(1L, new BigDecimal("10.00"),
                LocalDate.now().plusDays(1), null, false, 1L);

        sampleCharge = new LateReturnCharge();
        sampleCharge.setId(1L);
        sampleCharge.setAmount(new BigDecimal("10.00"));
        sampleCharge.setDueDate(LocalDate.now().plusDays(1));
        sampleCharge.setPaid(false);
        sampleCharge.setBorrowingRecord(sampleBorrowingRecord);
    }

    @Test
    public void testGetAllLateReturnCharges() {
        Page<LateReturnCharge> lateReturnCharges = new PageImpl<>(Collections.singletonList(sampleCharge));
        when(lateReturnChargeRepository.findAll(any(Pageable.class))).thenReturn(lateReturnCharges);

        Page<LateReturnChargeDTO> result = lateReturnChargeService.getAllLateReturnCharges(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(sampleChargeDTO, result.getContent().get(0));
    }

    @Test
    public void testGetLateReturnChargeById() {
        when(lateReturnChargeRepository.findById(anyLong())).thenReturn(Optional.of(sampleCharge));

        LateReturnChargeDTO result = lateReturnChargeService.getLateReturnChargeById(1L);

        assertNotNull(result);
        assertEquals(sampleChargeDTO, result);
    }

    @Test
    public void testGetLateReturnChargesByBorrowingRecordId() {
        when(lateReturnChargeRepository.findByBorrowingRecordId(anyLong())).thenReturn(Collections.singletonList(sampleCharge));

        List<LateReturnChargeDTO> result = lateReturnChargeService.getLateReturnChargesByBorrowingRecordId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleChargeDTO, result.get(0));
    }

    @Test
    public void testGetLateReturnChargesByPaymentStatus() {
        when(lateReturnChargeRepository.findByIsPaid(anyBoolean())).thenReturn(Collections.singletonList(sampleCharge));

        List<LateReturnChargeDTO> result = lateReturnChargeService.getLateReturnChargesByPaymentStatus(false);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleChargeDTO, result.get(0));
    }

    @Test
    public void testCreateLateReturnCharge() {
        when(borrowingRecordRepository.findById(anyLong())).thenReturn(Optional.of(sampleBorrowingRecord));
        when(lateReturnChargeRepository.save(any(LateReturnCharge.class))).thenReturn(sampleCharge);

        LateReturnChargeDTO result = lateReturnChargeService.createLateReturnCharge(sampleChargeDTO);

        assertNotNull(result);
        assertEquals(sampleChargeDTO, result);
    }

    @Test
    public void testUpdateLateReturnCharge() {
        when(lateReturnChargeRepository.findById(anyLong())).thenReturn(Optional.of(sampleCharge));
        when(lateReturnChargeRepository.save(any(LateReturnCharge.class))).thenReturn(sampleCharge);

        LateReturnChargeDTO updatedDTO = new LateReturnChargeDTO(1L, new BigDecimal("20.00"),
                LocalDate.now().plusDays(2), null, true, 1L);

        LateReturnChargeDTO result = lateReturnChargeService.updateLateReturnCharge(1L, updatedDTO);

        assertNotNull(result);
        assertEquals(updatedDTO.getAmount(), result.getAmount());
        assertEquals(updatedDTO.getDueDate(), result.getDueDate());
        assertEquals(updatedDTO.isPaid(), result.isPaid());
    }

    @Test
    public void testUpdateLateReturnChargeWithDifferentBorrowingRecord() {
        when(lateReturnChargeRepository.findById(anyLong())).thenReturn(Optional.of(sampleCharge));

        LateReturnChargeDTO updatedDTO = new LateReturnChargeDTO(1L, new BigDecimal("20.00"),
                LocalDate.now().plusDays(2), null, true, 2L);

        assertThrows(IllegalArgumentException.class, () -> lateReturnChargeService.updateLateReturnCharge(1L, updatedDTO));
    }

    @Test
    public void testDeleteLateReturnCharge() {
        when(lateReturnChargeRepository.findById(anyLong())).thenReturn(Optional.of(sampleCharge));

        lateReturnChargeService.deleteLateReturnCharge(1L);

        verify(lateReturnChargeRepository, times(1)).delete(any(LateReturnCharge.class));
    }

    @Test
    public void testDeleteLateReturnChargeNotFound() {
        when(lateReturnChargeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> lateReturnChargeService.deleteLateReturnCharge(1L));
    }
}
