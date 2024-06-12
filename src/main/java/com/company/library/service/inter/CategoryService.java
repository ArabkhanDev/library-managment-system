package com.company.library.service.inter;

import com.company.library.dto.common.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    Page<CategoryDTO> getAllCategories(Pageable pageable);

    List<CategoryDTO> getTopLevelCategories();

    CategoryDTO getCategoryById(Long id);

    List<CategoryDTO> getSubcategoriesByCategoryId(Long parentId);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(Long id);
}
