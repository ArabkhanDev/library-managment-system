package com.company.library.controller;

import com.company.library.dto.CategoryDTO;
import com.company.library.model.base.BaseResponse;
import com.company.library.service.inter.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public BaseResponse<Page<CategoryDTO>> getAllCategories(Pageable pageable) {
        Page<CategoryDTO> categories = categoryService.getAllCategories(pageable);
        return BaseResponse.success(categories);
    }

    @GetMapping("/top-level")
    public BaseResponse<List<CategoryDTO>> getTopLevelCategories() {
        List<CategoryDTO> categories = categoryService.getTopLevelCategories();
        return BaseResponse.success(categories);
    }

    @GetMapping("/{id}")
    public BaseResponse<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return BaseResponse.success(category);
    }

    @GetMapping("/{id}/subcategories")
    public BaseResponse<List<CategoryDTO>> getSubcategoriesByCategoryId(@PathVariable Long id) {
        List<CategoryDTO> subcategories = categoryService.getSubcategoriesByCategoryId(id);
        return BaseResponse.success(subcategories);
    }

    @PostMapping
    public BaseResponse<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return BaseResponse.created(createdCategory);
    }

    @PutMapping("/{id}")
    public BaseResponse<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return BaseResponse.success(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return BaseResponse.notContent();
    }
}
