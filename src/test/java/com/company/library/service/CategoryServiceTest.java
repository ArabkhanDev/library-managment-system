package com.company.library.service;

import com.company.library.dto.common.CategoryDTO;
import com.company.library.exception.types.ResourceNotFoundException;
import com.company.library.model.Category;
import com.company.library.repository.CategoryRepository;
import com.company.library.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category1, category2, subcategory1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category1 = new Category(1L, "Category 1", "Description 1", null);
        category2 = new Category(2L, "Category 2", "Description 2", null);
        subcategory1 = new Category(3L, "Subcategory 1", "Description 3", category1);
    }

    @Test
    void testGetAllCategories() {
        List<Category> categories = Arrays.asList(category1, category2);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> categoryPage = new PageImpl<>(categories, pageable, categories.size());
        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);

        Page<CategoryDTO> result = categoryService.getAllCategories(pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
    }

    @Test
    void testGetTopLevelCategories() {
        List<Category> topLevelCategories = Arrays.asList(category1, category2);
        when(categoryRepository.findByParentCategoryIsNull()).thenReturn(topLevelCategories);

        List<CategoryDTO> result = categoryService.getTopLevelCategories();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetCategoryById() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));

        CategoryDTO result = categoryService.getCategoryById(1L);

        assertNotNull(result);
        assertEquals(category1.getName(), result.getName());
    }

    @Test
    void testGetCategoryByIdNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(1L));
    }

    @Test
    void testGetSubcategoriesByCategoryId() {
        when(categoryRepository.findByParentCategoryId(1L)).thenReturn(Arrays.asList(subcategory1));

        List<CategoryDTO> result = categoryService.getSubcategoriesByCategoryId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(subcategory1.getName(), result.get(0).getName());
    }

    @Test
    void testCreateCategory() {
        CategoryDTO categoryDTO = new CategoryDTO(null, "New Category", "New Description", null);
        Category savedCategory = new Category(4L, "New Category", "New Description", null);
        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        CategoryDTO result = categoryService.createCategory(categoryDTO);

        assertNotNull(result);
        assertEquals(savedCategory.getName(), result.getName());
    }

    @Test
    void testCreateCategoryWithParent() {
        CategoryDTO categoryDTO = new CategoryDTO(null, "New Subcategory", "New Subcategory Description", 1L);
        Category savedCategory = new Category(4L, "New Subcategory", "New Subcategory Description", category1);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        CategoryDTO result = categoryService.createCategory(categoryDTO);

        assertNotNull(result);
        assertEquals(savedCategory.getName(), result.getName());
        assertEquals(categoryDTO.getParentCategoryId(), result.getParentCategoryId());
    }

    @Test
    void testUpdateCategory() {
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Updated Category", "Updated Description", null);
        Category existingCategory = new Category(1L, "Category 1", "Description 1", null);
        Category updatedCategory = new Category(1L, "Updated Category", "Updated Description", null);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        CategoryDTO result = categoryService.updateCategory(1L, categoryDTO);

        assertNotNull(result);
        assertEquals(updatedCategory.getName(), result.getName());
        assertEquals(updatedCategory.getDescription(), result.getDescription());
    }

    @Test
    void testUpdateCategoryWithParent() {
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Updated Category", "Updated Description", 2L);
        Category existingCategory = new Category(1L, "Category 1", "Description 1", null);
        Category updatedCategory = new Category(1L, "Updated Category", "Updated Description", category2);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category2));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        CategoryDTO result = categoryService.updateCategory(1L, categoryDTO);

        assertNotNull(result);
        assertEquals(updatedCategory.getName(), result.getName());
        assertEquals(updatedCategory.getDescription(), result.getDescription());
        assertEquals(categoryDTO.getParentCategoryId(), result.getParentCategoryId());
    }

    @Test
    void testDeleteCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        when(categoryRepository.findByParentCategoryId(1L)).thenReturn(Arrays.asList());

        assertDoesNotThrow(() -> categoryService.deleteCategory(1L));
        verify(categoryRepository, times(1)).delete(category1);
    }

    @Test
    void testDeleteCategoryWithSubcategories() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        when(categoryRepository.findByParentCategoryId(1L)).thenReturn(Arrays.asList(subcategory1));

        assertThrows(IllegalStateException.class, () -> categoryService.deleteCategory(1L));
        verify(categoryRepository, never()).delete(any(Category.class));
    }
}