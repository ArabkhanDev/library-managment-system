package com.company.library.mapper;

import com.company.library.dto.MemberDTO;
import com.company.library.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberDTO toDTO(Member member);

    Member toEntity(MemberDTO memberDTO);

}
