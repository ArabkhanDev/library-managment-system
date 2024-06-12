package com.company.library.controller;

import com.company.library.dto.common.LateReturnChargeDTO;
import com.company.library.model.base.BaseResponse;
import com.company.library.service.inter.LateReturnChargeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/late-return-charges")
public class LateReturnChargeController {

    private final LateReturnChargeService lateReturnChargeService;

    @GetMapping
    public BaseResponse<Page<LateReturnChargeDTO>> getAllLateReturnCharges(Pageable pageable) {
        Page<LateReturnChargeDTO> charges = lateReturnChargeService.getAllLateReturnCharges(pageable);
        return BaseResponse.success(charges);
    }

    @GetMapping("/{id}")
    public BaseResponse<LateReturnChargeDTO> getLateReturnChargeById(@PathVariable Long id) {
        LateReturnChargeDTO charge = lateReturnChargeService.getLateReturnChargeById(id);
        return BaseResponse.success(charge);
    }

    @GetMapping("/by-borrowing-record/{borrowingRecordId}")
    public BaseResponse<List<LateReturnChargeDTO>> getLateReturnChargesByBorrowingRecordId(@PathVariable Long borrowingRecordId) {
        List<LateReturnChargeDTO> charges = lateReturnChargeService.getLateReturnChargesByBorrowingRecordId(borrowingRecordId);
        return BaseResponse.success(charges);
    }

    @GetMapping("/by-payment-status")
    public BaseResponse<List<LateReturnChargeDTO>> getLateReturnChargesByPaymentStatus(@RequestParam boolean isPaid) {
        List<LateReturnChargeDTO> charges = lateReturnChargeService.getLateReturnChargesByPaymentStatus(isPaid);
        return BaseResponse.success(charges);
    }

    @PostMapping
    public BaseResponse<LateReturnChargeDTO> createLateReturnCharge(@Valid @RequestBody LateReturnChargeDTO lateReturnChargeDTO) {
        LateReturnChargeDTO createdCharge = lateReturnChargeService.createLateReturnCharge(lateReturnChargeDTO);
        return BaseResponse.created(createdCharge);
    }

    @PutMapping("/{id}")
    public BaseResponse<LateReturnChargeDTO> updateLateReturnCharge(@PathVariable Long id,@Valid @RequestBody LateReturnChargeDTO lateReturnChargeDTO) {
        LateReturnChargeDTO updatedCharge = lateReturnChargeService.updateLateReturnCharge(id, lateReturnChargeDTO);
        return BaseResponse.success(updatedCharge);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteLateReturnCharge(@PathVariable Long id) {
        lateReturnChargeService.deleteLateReturnCharge(id);
        return BaseResponse.notContent();
    }
}
