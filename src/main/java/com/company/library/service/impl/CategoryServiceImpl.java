package com.company.library.service.impl;

import com.company.library.dto.common.CategoryDTO;
import com.company.library.exception.types.ResourceNotFoundException;
import com.company.library.mapper.CategoryMapper;
import com.company.library.model.Category;
import com.company.library.repository.CategoryRepository;
import com.company.library.service.inter.CategoryService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Page<CategoryDTO> getAllCategories(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(CategoryMapper.INSTANCE::toDTO);
    }

    @Override
    public List<CategoryDTO> getTopLevelCategories() {
        return categoryRepository.findByParentCategoryIsNull().stream()
                .map(CategoryMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryMapper.INSTANCE::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Override
    public List<CategoryDTO> getSubcategoriesByCategoryId(Long parentId) {
        return categoryRepository.findByParentCategoryId(parentId).stream()
                .map(CategoryMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = CategoryMapper.INSTANCE.toEntity(categoryDTO);

        Optional.ofNullable(categoryDTO.getParentCategoryId())
                .ifPresent(parentCategoryId -> {
                    Category parentCategory = categoryRepository.findById(parentCategoryId)
                            .orElseThrow(() -> new ResourceNotFoundException("Parent category not found with id: " + parentCategoryId));
                    category.setParentCategory(parentCategory);
                });

        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.INSTANCE.toDTO(savedCategory);
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        existingCategory.setName(categoryDTO.getName());
        existingCategory.setDescription(categoryDTO.getDescription());

        Optional.ofNullable(categoryDTO.getParentCategoryId())
                .flatMap(categoryRepository::findById)
                .ifPresentOrElse(existingCategory::setParentCategory,
                        () -> existingCategory.setParentCategory(null));

        Category updatedCategory = categoryRepository.save(existingCategory);
        return CategoryMapper.INSTANCE.toDTO(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        List<Category> subcategories = categoryRepository.findByParentCategoryId(id);
        if (!subcategories.isEmpty()) {
            throw new IllegalStateException("Cannot delete category with subcategories. Delete or reassign subcategories first.");
        }

        categoryRepository.delete(category);
    }
}