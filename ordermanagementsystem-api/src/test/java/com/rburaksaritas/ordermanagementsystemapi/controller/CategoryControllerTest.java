package com.rburaksaritas.ordermanagementsystemapi.controller;

import com.rburaksaritas.ordermanagementsystemapi.dto.CategoryDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryControllerTests {

    private CategoryController categoryController;
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        categoryService = mock(CategoryService.class);
        categoryController = new CategoryController(categoryService);
    }

    @Test
    void getAllCategories_ReturnsAllCategoriesSuccessfully() {
        // Arrange
        List<CategoryDTO> expectedCategories = new ArrayList<>();
        expectedCategories.add(new CategoryDTO(1, "Category 1", "Detail 1", new Date()));
        expectedCategories.add(new CategoryDTO(2, "Category 2", "Detail 2", new Date()));
        when(categoryService.getAllCategories()).thenReturn(expectedCategories);

        // Act
        ResponseEntity<List<CategoryDTO>> responseEntity = categoryController.getAllCategories();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedCategories.size(), responseEntity.getBody().size());
    }

    @Test
    void getCategoryById_ValidCategoryId_ReturnsCategorySuccessfully() {
        // Arrange
        int categoryId = 1;
        CategoryDTO expectedCategory = new CategoryDTO(categoryId, "Test Category", "Test Details", new Date());
        when(categoryService.getCategoryById(categoryId)).thenReturn(expectedCategory);

        // Act
        ResponseEntity<CategoryDTO> responseEntity = categoryController.getCategoryById(categoryId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedCategory.getId(), responseEntity.getBody().getId());
        assertEquals(expectedCategory.getName(), responseEntity.getBody().getName());
    }

    @Test
    void getCategoryById_CategoryNotFound_ReturnsNotFound() {
        // Arrange
        int categoryId = 1;
        when(categoryService.getCategoryById(categoryId)).thenReturn(null);

        // Act
        ResponseEntity<CategoryDTO> responseEntity = categoryController.getCategoryById(categoryId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void addCategory_ValidCategory_ReturnsCreatedCategory() {
        // Arrange
        CategoryDTO newCategory = new CategoryDTO(null, "New Category", "New Details", new Date());
        CategoryDTO expectedSavedCategory = new CategoryDTO(1, "New Category", "New Details", new Date());
        when(categoryService.saveCategory(newCategory)).thenReturn(expectedSavedCategory);

        // Act
        ResponseEntity<CategoryDTO> responseEntity = categoryController.addCategory(newCategory);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedSavedCategory.getId(), responseEntity.getBody().getId());
        assertEquals(expectedSavedCategory.getName(), responseEntity.getBody().getName());
    }

    @Test
    void addCategory_DuplicateCategoryName_ReturnsBadRequest() {
        // Arrange
        CategoryDTO newCategory = new CategoryDTO(null, "Duplicate Category", "New Details", new Date());
        when(categoryService.saveCategory(newCategory)).thenThrow(new IllegalArgumentException("Category name must be unique."));

        // Act
        ResponseEntity<CategoryDTO> responseEntity = categoryController.addCategory(newCategory);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void updateCategory_ValidCategory_ReturnsUpdatedCategory() {
        // Arrange
        int categoryId = 1;
        CategoryDTO updatedCategory = new CategoryDTO(categoryId, "Updated Category", "Updated Details", new Date());
        when(categoryService.updateCategory(categoryId, updatedCategory.getName(), updatedCategory.getDetails(), updatedCategory.getTimestamp()))
                .thenReturn(updatedCategory);

        // Act
        ResponseEntity<CategoryDTO> responseEntity = categoryController.updateCategory(categoryId, updatedCategory.getName(), updatedCategory.getDetails(), updatedCategory.getTimestamp());

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updatedCategory.getId(), responseEntity.getBody().getId());
        assertEquals(updatedCategory.getName(), responseEntity.getBody().getName());
        assertEquals(updatedCategory.getDetails(), responseEntity.getBody().getDetails());
    }

    @Test
    void updateCategory_CategoryNotFound_ReturnsNotFound() {
        // Arrange
        int categoryId = 1;
        CategoryDTO updatedCategory = new CategoryDTO(categoryId, "Updated Category", "Updated Details", new Date());
        when(categoryService.updateCategory(categoryId, updatedCategory.getName(), updatedCategory.getDetails(), updatedCategory.getTimestamp()))
                .thenThrow(new ResourceNotFoundException("Category", "id", categoryId));

        // Act
        ResponseEntity<CategoryDTO> responseEntity = categoryController.updateCategory(categoryId, updatedCategory.getName(), updatedCategory.getDetails(), updatedCategory.getTimestamp());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void updateCategory_DuplicateCategoryName_ReturnsBadRequest() {
        // Arrange
        int categoryId = 1;
        CategoryDTO updatedCategory = new CategoryDTO(categoryId, "Updated Category", "Updated Details", new Date());
        when(categoryService.updateCategory(categoryId, updatedCategory.getName(), updatedCategory.getDetails(), updatedCategory.getTimestamp()))
                .thenThrow(new IllegalArgumentException("Category name must be unique."));

        // Act
        ResponseEntity<CategoryDTO> responseEntity = categoryController.updateCategory(categoryId, updatedCategory.getName(), updatedCategory.getDetails(), updatedCategory.getTimestamp());

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void deleteCategory_ValidCategoryId_ReturnsNoContent() {
        // Arrange
        int categoryId = 1;

        // Act
        ResponseEntity<Void> responseEntity = categoryController.deleteCategory(categoryId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteCategory_CategoryNotFound_ReturnsNotFound() {
        // Arrange
        int categoryId = 1;
        doThrow(new ResourceNotFoundException("Category", "id", categoryId)).when(categoryService).deleteCategory(categoryId);

        // Act
        ResponseEntity<Void> responseEntity = categoryController.deleteCategory(categoryId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void deleteCategory_AssociatedCategory_ReturnsBadRequest() {
        // Arrange
        int categoryId = 1;
        doThrow(new IllegalArgumentException("Category with id " + categoryId + " is associated with existing products and cannot be deleted."))
                .when(categoryService).deleteCategory(categoryId);

        // Act
        ResponseEntity<Void> responseEntity = categoryController.deleteCategory(categoryId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
