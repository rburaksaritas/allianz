package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.CategoryDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.model.Category;
import com.rburaksaritas.ordermanagementsystemapi.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTests {

    private CategoryService categoryService;
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        modelMapper = new ModelMapper();
        categoryService = new CategoryServiceImpl(categoryRepository, modelMapper);
    }

    @Test
    public void CategoryService_GetAll_ReturnsAllCategories() {
        // Arrange
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Category 1", "Detail 1",new Date()));
        categories.add(new Category(2, "Category 2", "Detail 2", new Date()));
        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();

        // Assert
        assertNotNull(categoryDTOList);
        assertEquals(categories.size(), categoryDTOList.size());
    }

    @Test
    public void CategoryService_GetById_ValidCategoryReturnsCategory() {
        // Arrange
        int categoryId = 1;
        Category category = new Category(1, "Test Category", "Test Detail", new Date());
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Act
        CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);

        // Assert
        assertNotNull(categoryDTO);
        assertEquals(category.getId(), categoryDTO.getId());
        assertEquals(category.getName(), categoryDTO.getName());
    }

    @Test
    public void CategoryService_GetById_CategoryNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int categoryId = 1;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(categoryId));
    }

    @Test
    public void CategoryService_SaveCategory_ValidCategoryDTO_ReturnsSavedCategoryDTO() {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Test Category");
        Category category = modelMapper.map(categoryDTO, Category.class);
        when(categoryRepository.save(category)).thenReturn(category);

        // Act
        CategoryDTO savedCategoryDTO = categoryService.saveCategory(categoryDTO);

        // Assert
        assertNotNull(savedCategoryDTO);
        assertEquals(category.getId(), savedCategoryDTO.getId());
        assertEquals(category.getName(), savedCategoryDTO.getName());
    }

    @Test
    public void CategoryService_SaveCategory_DuplicateCategoryNameThrowsIllegalArgumentException() {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Test Category");

        when(categoryRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> categoryService.saveCategory(categoryDTO));
    }

    @Test
    public void CategoryService_UpdateCategory_ValidCategoryIdAndDTO_ReturnsUpdatedCategoryDTO() {
        // Arrange
        int categoryId = 1;
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Updated Category");
        Category existingCategory = new Category(categoryId, "Test Category", "Test Details", new Date());
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any())).thenReturn(existingCategory);

        // Act
        CategoryDTO updatedCategoryDTO = categoryService.updateCategory(categoryId, categoryDTO);

        // Assert
        assertNotNull(updatedCategoryDTO);
        assertEquals(categoryId, updatedCategoryDTO.getId());
        assertEquals(categoryDTO.getName(), updatedCategoryDTO.getName());
    }

    @Test
    public void CategoryService_UpdateCategory_CategoryNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int categoryId = 1;
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Updated Category");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> categoryService.updateCategory(categoryId, categoryDTO));
    }

    @Test
    public void CategoryService_Delete_ValidCategoryId_DeletesCategory() {
        // Arrange
        int categoryId = 1;

        // Act
        assertDoesNotThrow(() -> categoryService.deleteCategory(categoryId));

        // Assert
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }


    @Test
    public void CategoryService_Delete_AssociatedCategoryThrowsIllegalArgumentException() {
        // Arrange
        int categoryId = 1;
        doThrow(DataIntegrityViolationException.class).when(categoryRepository).deleteById(categoryId);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> categoryService.deleteCategory(categoryId));
    }

    // Add more test methods for other service methods...

}
