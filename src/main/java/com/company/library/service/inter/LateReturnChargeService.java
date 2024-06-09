package com.company.library.service.inter;

import com.company.library.dto.LateReturnChargeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LateReturnChargeService {

    Page<LateReturnChargeDTO> getAllLateReturnCharges(Pageable pageable);

    LateReturnChargeDTO getLateReturnChargeById(Long id);

    List<LateReturnChargeDTO> getLateReturnChargesByBorrowingRecordId(Long borrowingRecordId);

    List<LateReturnChargeDTO> getLateReturnChargesByPaymentStatus(boolean isPaid);

    LateReturnChargeDTO createLateReturnCharge(LateReturnChargeDTO lateReturnChargeDTO);

    LateReturnChargeDTO updateLateReturnCharge(Long id, LateReturnChargeDTO lateReturnChargeDTO);

    void deleteLateReturnCharge(Long id);

}
