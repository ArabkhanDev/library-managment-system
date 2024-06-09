package com.company.library.service.inter;

import com.company.library.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    List<CategoryDTO> getTopLevelCategories();

    CategoryDTO getCategoryById(Long id);

    List<CategoryDTO> getSubcategoriesByCategoryId(Long parentId);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(Long id);
}
