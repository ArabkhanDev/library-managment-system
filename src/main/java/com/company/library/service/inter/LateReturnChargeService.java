package com.company.library.service.inter;

import com.company.library.dto.LateReturnChargeDTO;

import java.util.List;

public interface LateReturnChargeService {

    public List<LateReturnChargeDTO> getAllLateReturnCharges();

    public LateReturnChargeDTO getLateReturnChargeById(Long id);

    public List<LateReturnChargeDTO> getLateReturnChargesByBorrowingRecordId(Long borrowingRecordId);

    public List<LateReturnChargeDTO> getLateReturnChargesByPaymentStatus(boolean isPaid);

    public LateReturnChargeDTO createLateReturnCharge(LateReturnChargeDTO lateReturnChargeDTO);

    public LateReturnChargeDTO updateLateReturnCharge(Long id, LateReturnChargeDTO lateReturnChargeDTO);

    public void deleteLateReturnCharge(Long id);

}
