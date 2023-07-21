package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.CategoryDTO;
import com.rburaksaritas.ordermanagementsystemapi.dto.ProductDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.model.Category;
import com.rburaksaritas.ordermanagementsystemapi.model.Product;
import com.rburaksaritas.ordermanagementsystemapi.repository.CategoryRepository;
import com.rburaksaritas.ordermanagementsystemapi.repository.ProductRepository;
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

class ProductServiceTests {

    private CategoryService categoryService;
    private CategoryRepository categoryRepository;
    private ModelMapper categoryModelMapper;

    private ProductService productService;
    private ProductRepository productRepository;
    private ModelMapper productModelMapper;

    @BeforeEach
    public void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryModelMapper = new ModelMapper();
        categoryService = new CategoryServiceImpl(categoryRepository, categoryModelMapper);

        productRepository = mock(ProductRepository.class);
        productModelMapper = new ModelMapper();
        productService = new ProductServiceImpl(productRepository, productModelMapper);
    }

    // CategoryService Tests
    @Test
    public void CategoryService_GetAll_ReturnsAllCategories() {
        // Arrange
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Category 1", "Detail 1", new Date()));
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
        Category category = categoryModelMapper.map(categoryDTO, Category.class);
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
    public void ProductService_UpdateProduct_ValidProductIdAndData_ReturnsUpdatedProductDTO() {
        // Arrange
        int productId = 1;
        String updatedName = "Updated Product";
        Double updatedPrice = 15.0;
        String updatedThumbnail = "updated_thumbnail.jpg";
        String updatedDetail = "Updated Product Details";
        int categoryId = 1; // Category ID

        // Creating Category and CategoryDTO
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Category 1");
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryId);
        categoryDTO.setName("Category 1");

        Integer newQuantity = 50;
        Date newDate = new Date();

        // Creating ProductDTO
        ProductDTO productDTO = new ProductDTO(productId, updatedName, updatedPrice, updatedThumbnail, updatedDetail, categoryDTO, newQuantity, newDate);
        Product existingProduct = new Product(productId, "Test Product", 10.0, "thumbnail", "Test Details", category, 100, new Date());
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category)); // Mock category fetch

        // Act
        ProductDTO updatedProductDTO = productService.updateProduct(productId, updatedName, updatedPrice.toString(), updatedThumbnail, updatedDetail, categoryId, newQuantity, newDate);

        // Assert
        assertNotNull(updatedProductDTO);
        assertEquals(productId, updatedProductDTO.getId());
        assertEquals(updatedName, updatedProductDTO.getName());
        assertEquals(updatedPrice, updatedProductDTO.getPrice());
        assertEquals(updatedThumbnail, updatedProductDTO.getThumbnail());
        assertEquals(updatedDetail, updatedProductDTO.getDetails());
        assertEquals(categoryDTO.getId(), updatedProductDTO.getCategory().getId());
        assertEquals(categoryDTO.getName(), updatedProductDTO.getCategory().getName());
        assertEquals(newQuantity, updatedProductDTO.getQuantity());
    }

    @Test
    public void ProductService_UpdateProduct_ProductNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int productId = 1;
        String updatedName = "Updated Product";
        Double updatedPrice = 15.0;
        String updatedThumbnail = "updated_thumbnail.jpg";
        String updatedDetail = "Updated Product Details";
        int categoryId = 1; // Category ID

        // Creating Category and CategoryDTO
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Category 1");
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryId);
        categoryDTO.setName("Category 1");

        Integer newQuantity = 50;
        Date newDate = new Date();

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(productId, updatedName, updatedPrice.toString(), updatedThumbnail, updatedDetail, categoryId, newQuantity, newDate));
    }

    @Test
    public void ProductService_UpdateProduct_CategoryNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int productId = 1;
        String updatedName = "Updated Product";
        Double updatedPrice = 15.0;
        String updatedThumbnail = "updated_thumbnail.jpg";
        String updatedDetail = "Updated Product Details";
        int categoryId = 1; // Category ID

        // Creating Category and CategoryDTO
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Category 1");
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryId);
        categoryDTO.setName("Category 1");

        Integer newQuantity = 50;
        Date newDate = new Date();

        // Mocking the product fetch
        Product existingProduct = new Product(productId, "Test Product", 10.0, "thumbnail", "Test Details", category, 100, new Date());
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // Mocking the category fetch with an empty Optional to simulate category not found
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(productId, updatedName, updatedPrice.toString(), updatedThumbnail, updatedDetail, categoryId, newQuantity, newDate));
    }

    @Test
    public void CategoryService_Delete_ValidCategoryId_DeletesCategory() {
        // Arrange
        int categoryId = 1;
        Category existingCategory = new Category(categoryId, "Test Category", "Test Details", new Date());
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        // Act
        assertDoesNotThrow(() -> categoryService.deleteCategory(categoryId));

        // Assert
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    public void CategoryService_Delete_CategoryNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int categoryId = 1;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(categoryId));
    }

    @Test
    public void CategoryService_Delete_AssociatedCategoryThrowsIllegalArgumentException() {
        // Arrange
        int categoryId = 1;
        Category existingCategory = new Category(categoryId, "Test Category", "Test Details", new Date());
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        doThrow(DataIntegrityViolationException.class).when(categoryRepository).deleteById(categoryId);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> categoryService.deleteCategory(categoryId));
    }
}
