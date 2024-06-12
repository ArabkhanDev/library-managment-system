package com.company.library.mapper;

import com.company.library.dto.common.BorrowingRecordDTO;
import com.company.library.model.BorrowingRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BorrowingRecordMapper {
    BorrowingRecordMapper INSTANCE = Mappers.getMapper(BorrowingRecordMapper.class);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "member.id", target = "memberId")
    BorrowingRecordDTO toDTO(BorrowingRecord borrowingRecord);

    @Mapping(target = "book", ignore = true)
    @Mapping(target = "member", ignore = true)
    BorrowingRecord toEntity(BorrowingRecordDTO borrowingRecordDTO);
}
