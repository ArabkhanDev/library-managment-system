package com.company.library.mapper;

import com.company.library.dto.common.LateReturnChargeDTO;
import com.company.library.model.LateReturnCharge;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LateReturnChargeMapper {
    LateReturnChargeMapper INSTANCE = Mappers.getMapper(LateReturnChargeMapper.class);

    @Mapping(source = "borrowingRecord.id", target = "borrowingRecordId")
    LateReturnChargeDTO toDTO(LateReturnCharge lateReturnCharge);

    @Mapping(target = "borrowingRecord", ignore = true)
    LateReturnCharge toEntity(LateReturnChargeDTO lateReturnChargeDTO);
}
