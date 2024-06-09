package com.company.library.controller;

import com.company.library.dto.LateReturnChargeDTO;
import com.company.library.service.inter.LateReturnChargeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/late-return-charges")
public class LateReturnChargeController {

    private final LateReturnChargeService lateReturnChargeService;

    @GetMapping
    public ResponseEntity<List<LateReturnChargeDTO>> getAllLateReturnCharges() {
        List<LateReturnChargeDTO> charges = lateReturnChargeService.getAllLateReturnCharges();
        return ResponseEntity.ok(charges);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LateReturnChargeDTO> getLateReturnChargeById(@PathVariable Long id) {
        LateReturnChargeDTO charge = lateReturnChargeService.getLateReturnChargeById(id);
        return ResponseEntity.ok(charge);
    }

    @GetMapping("/by-borrowing-record/{borrowingRecordId}")
    public ResponseEntity<List<LateReturnChargeDTO>> getLateReturnChargesByBorrowingRecordId(@PathVariable Long borrowingRecordId) {
        List<LateReturnChargeDTO> charges = lateReturnChargeService.getLateReturnChargesByBorrowingRecordId(borrowingRecordId);
        return ResponseEntity.ok(charges);
    }

    @GetMapping("/by-payment-status")
    public ResponseEntity<List<LateReturnChargeDTO>> getLateReturnChargesByPaymentStatus(@RequestParam boolean isPaid) {
        List<LateReturnChargeDTO> charges = lateReturnChargeService.getLateReturnChargesByPaymentStatus(isPaid);
        return ResponseEntity.ok(charges);
    }

    @PostMapping
    public ResponseEntity<LateReturnChargeDTO> createLateReturnCharge(@Valid @RequestBody LateReturnChargeDTO lateReturnChargeDTO) {
        LateReturnChargeDTO createdCharge = lateReturnChargeService.createLateReturnCharge(lateReturnChargeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCharge);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LateReturnChargeDTO> updateLateReturnCharge(@PathVariable Long id,@Valid @RequestBody LateReturnChargeDTO lateReturnChargeDTO) {
        LateReturnChargeDTO updatedCharge = lateReturnChargeService.updateLateReturnCharge(id, lateReturnChargeDTO);
        return ResponseEntity.ok(updatedCharge);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLateReturnCharge(@PathVariable Long id) {
        lateReturnChargeService.deleteLateReturnCharge(id);
        return ResponseEntity.noContent().build();
    }
}
