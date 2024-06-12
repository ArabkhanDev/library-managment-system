package com.company.library.mapper;

import com.company.library.dto.common.PublishingHouseDTO;
import com.company.library.model.PublishingHouse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublishingHouseMapper {
    PublishingHouseMapper INSTANCE = Mappers.getMapper(PublishingHouseMapper.class);

    PublishingHouseDTO toDTO(PublishingHouse publishingHouse);

    PublishingHouse toEntity(PublishingHouseDTO publishingHouseDTO);
}
