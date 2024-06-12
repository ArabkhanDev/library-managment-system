package com.company.library.mapper;

import com.company.library.dto.common.CategoryDTO;
import com.company.library.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(source = "parentCategory.id", target = "parentCategoryId")
    CategoryDTO toDTO(Category category);

    @Mapping(target = "parentCategory", ignore = true)
    Category toEntity(CategoryDTO categoryDTO);
}
