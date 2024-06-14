package com.company.library.service.impl;

import com.company.library.dto.common.LateReturnChargeDTO;
import com.company.library.exception.types.ResourceNotFoundException;
import com.company.library.mapper.LateReturnChargeMapper;
import com.company.library.model.BorrowingRecord;
import com.company.library.model.LateReturnCharge;
import com.company.library.repository.BorrowingRecordRepository;
import com.company.library.repository.LateReturnChargeRepository;
import com.company.library.service.inter.LateReturnChargeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LateReturnChargeServiceImpl implements LateReturnChargeService {

    private final LateReturnChargeRepository lateReturnChargeRepository;
    private final BorrowingRecordRepository borrowingRecordRepository;

    @Override
    public Page<LateReturnChargeDTO> getAllLateReturnCharges(Pageable pageable) {
        Page<LateReturnCharge> lateReturnCharges = lateReturnChargeRepository.findAll(pageable);
        return lateReturnCharges.map(LateReturnChargeMapper.INSTANCE::toDTO);
    }

    @Override
    public LateReturnChargeDTO getLateReturnChargeById(Long id) {
        return lateReturnChargeRepository.findById(id)
                .map(LateReturnChargeMapper.INSTANCE::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Late return charge not found with id: " + id));
    }

    @Override
    public List<LateReturnChargeDTO> getLateReturnChargesByBorrowingRecordId(Long borrowingRecordId) {
        return lateReturnChargeRepository.findByBorrowingRecordId(borrowingRecordId).stream()
                .map(LateReturnChargeMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LateReturnChargeDTO> getLateReturnChargesByPaymentStatus(boolean isPaid) {
        return lateReturnChargeRepository.findByIsPaid(isPaid).stream()
                .map(LateReturnChargeMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LateReturnChargeDTO createLateReturnCharge(LateReturnChargeDTO lateReturnChargeDTO) {
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findById(lateReturnChargeDTO.getBorrowingRecordId())
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing record not found with id: " + lateReturnChargeDTO.getBorrowingRecordId()));

        LateReturnCharge lateReturnCharge = LateReturnChargeMapper.INSTANCE.toEntity(lateReturnChargeDTO);
        lateReturnCharge.setBorrowingRecord(borrowingRecord);

        LateReturnCharge savedCharge = lateReturnChargeRepository.save(lateReturnCharge);
        return LateReturnChargeMapper.INSTANCE.toDTO(savedCharge);
    }

    @Override
    public LateReturnChargeDTO updateLateReturnCharge(Long id, LateReturnChargeDTO lateReturnChargeDTO) {
        LateReturnCharge existingCharge = lateReturnChargeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Late return charge not found with id: " + id));

        existingCharge.setAmount(lateReturnChargeDTO.getAmount());
        existingCharge.setDueDate(lateReturnChargeDTO.getDueDate());
        existingCharge.setPaymentDate(lateReturnChargeDTO.getPaymentDate());
        existingCharge.setPaid(lateReturnChargeDTO.isPaid());

        if (!existingCharge.getBorrowingRecord().getId().equals(lateReturnChargeDTO.getBorrowingRecordId())) {
            throw new IllegalArgumentException("Cannot change the borrowing record for a late return charge.");
        }

        LateReturnCharge updatedCharge = lateReturnChargeRepository.save(existingCharge);
        return LateReturnChargeMapper.INSTANCE.toDTO(updatedCharge);
    }

    @Override
    public void deleteLateReturnCharge(Long id) {
        LateReturnCharge lateReturnCharge = lateReturnChargeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Late return charge not found with id: " + id));
        lateReturnChargeRepository.delete(lateReturnCharge);
    }
}
