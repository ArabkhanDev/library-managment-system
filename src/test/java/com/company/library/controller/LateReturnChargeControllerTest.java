package com.company.library.controller;

import com.company.library.dto.common.LateReturnChargeDTO;
import com.company.library.model.LateReturnCharge;
import com.company.library.service.inter.LateReturnChargeService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LateReturnChargeControllerTest {

    @Mock
    private LateReturnChargeService lateReturnChargeService;

    @InjectMocks
    private LateReturnChargeController lateReturnChargeController;

    private LateReturnChargeDTO lateReturnChargeDTO;
    private LateReturnCharge lateReturnCharge;

    @BeforeEach
    void setUp() {
        lateReturnChargeDTO = new LateReturnChargeDTO(1L, new BigDecimal("10.0"), LocalDate.now().plusDays(1), null, false, 1L);
        lateReturnCharge = new LateReturnCharge(1L, new BigDecimal("10.0"), LocalDate.now().plusDays(1), null, false, null);
    }

    @Test
    public void testGetAllLateReturnCharges() {
        Page<LateReturnChargeDTO> page = new PageImpl<>(Collections.singletonList(lateReturnChargeDTO));
        when(lateReturnChargeService.getAllLateReturnCharges(any(Pageable.class))).thenReturn(page);

        Page<LateReturnChargeDTO> result = lateReturnChargeController.getAllLateReturnCharges(Pageable.unpaged()).getData();

        assertEquals(page.getContent(), result.getContent());
    }

    @Test
    public void testGetLateReturnChargeById() {
        when(lateReturnChargeService.getLateReturnChargeById(1L)).thenReturn(lateReturnChargeDTO);

        LateReturnChargeDTO result = lateReturnChargeController.getLateReturnChargeById(1L).getData();

        assertEquals(lateReturnChargeDTO, result);
    }

    @Test
    public void testGetLateReturnChargesByBorrowingRecordId() {
        List<LateReturnChargeDTO> charges = Collections.singletonList(lateReturnChargeDTO);
        when(lateReturnChargeService.getLateReturnChargesByBorrowingRecordId(1L)).thenReturn(charges);

        List<LateReturnChargeDTO> result = lateReturnChargeController.getLateReturnChargesByBorrowingRecordId(1L).getData();

        assertEquals(charges, result);
    }

    @Test
    public void testGetLateReturnChargesByPaymentStatus() {
        List<LateReturnChargeDTO> charges = Collections.singletonList(lateReturnChargeDTO);
        when(lateReturnChargeService.getLateReturnChargesByPaymentStatus(false)).thenReturn(charges);

        List<LateReturnChargeDTO> result = lateReturnChargeController.getLateReturnChargesByPaymentStatus(false).getData();

        assertEquals(charges, result);
    }

    @Test
    public void testCreateLateReturnCharge() {
        when(lateReturnChargeService.createLateReturnCharge(any(LateReturnChargeDTO.class))).thenReturn(lateReturnChargeDTO);

        LateReturnChargeDTO result = lateReturnChargeController.createLateReturnCharge(lateReturnChargeDTO).getData();

        assertEquals(lateReturnChargeDTO, result);
    }

    @Test
    public void testUpdateLateReturnCharge() {
        when(lateReturnChargeService.updateLateReturnCharge(eq(1L), any(LateReturnChargeDTO.class))).thenReturn(lateReturnChargeDTO);

        LateReturnChargeDTO result = lateReturnChargeController.updateLateReturnCharge(1L, lateReturnChargeDTO).getData();

        assertEquals(lateReturnChargeDTO, result);
    }

    @Test
    public void testDeleteLateReturnCharge() {
        doNothing().when(lateReturnChargeService).deleteLateReturnCharge(1L);

        lateReturnChargeController.deleteLateReturnCharge(1L);

        verify(lateReturnChargeService, times(1)).deleteLateReturnCharge(1L);
    }
}
