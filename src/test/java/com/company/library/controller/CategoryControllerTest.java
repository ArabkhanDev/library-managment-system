package com.company.library.controller;

import com.company.library.dto.common.CategoryDTO;
import com.company.library.model.Category;
import com.company.library.service.inter.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private CategoryDTO categoryDTO;
    private Category category;

    @BeforeEach
    void setUp() {
        categoryDTO = new CategoryDTO(1L, "TestCategory", "Test Description", null);
        category = new Category(1L, "TestCategory", "Test Description", null);
    }

    @Test
    public void testGetAllCategories() {
        Page<CategoryDTO> page = new PageImpl<>(Collections.singletonList(categoryDTO));
        when(categoryService.getAllCategories(any(Pageable.class))).thenReturn(page);

        Page<CategoryDTO> result = categoryController.getAllCategories(Pageable.unpaged()).getData();

        assertEquals(page.getContent(), result.getContent());
    }

    @Test
    public void testGetTopLevelCategories() {
        List<CategoryDTO> categories = Collections.singletonList(categoryDTO);
        when(categoryService.getTopLevelCategories()).thenReturn(categories);

        List<CategoryDTO> result = categoryController.getTopLevelCategories().getData();

        assertEquals(categories, result);
    }

    @Test
    public void testGetCategoryById() {
        when(categoryService.getCategoryById(1L)).thenReturn(categoryDTO);

        CategoryDTO result = categoryController.getCategoryById(1L).getData();

        assertEquals(categoryDTO, result);
    }

    @Test
    public void testGetSubcategoriesByCategoryId() {
        List<CategoryDTO> subcategories = Collections.singletonList(categoryDTO);
        when(categoryService.getSubcategoriesByCategoryId(1L)).thenReturn(subcategories);

        List<CategoryDTO> result = categoryController.getSubcategoriesByCategoryId(1L).getData();

        assertEquals(subcategories, result);
    }

    @Test
    public void testCreateCategory() {
        when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(categoryDTO);

        CategoryDTO result = categoryController.createCategory(categoryDTO).getData();

        assertEquals(categoryDTO, result);
    }

    @Test
    public void testUpdateCategory() {
        when(categoryService.updateCategory(eq(1L), any(CategoryDTO.class))).thenReturn(categoryDTO);

        CategoryDTO result = categoryController.updateCategory(1L, categoryDTO).getData();

        assertEquals(categoryDTO, result);
    }

    @Test
    public void testDeleteCategory() {
        doNothing().when(categoryService).deleteCategory(1L);

        categoryController.deleteCategory(1L);

        verify(categoryService, times(1)).deleteCategory(1L);
    }
}
