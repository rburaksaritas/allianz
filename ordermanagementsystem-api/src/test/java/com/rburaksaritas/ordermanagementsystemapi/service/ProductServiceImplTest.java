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
        productService = new ProductServiceImpl(productRepository, categoryRepository, productModelMapper);
    }

    // ProductService Tests
    @Test
    public void ProductService_GetAll_ReturnsAllProducts() {
        // Arrange
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Product 1", 10.0, "thumbnail1", "Detail 1", new Category(), 100, new Date()));
        products.add(new Product(2, "Product 2", 20.0, "thumbnail2", "Detail 2", new Category(), 50, new Date()));
        when(productRepository.findAll()).thenReturn(products);

        // Act
        List<ProductDTO> productDTOList = productService.getAllProducts();

        // Assert
        assertNotNull(productDTOList);
        assertEquals(products.size(), productDTOList.size());
    }

    @Test
    public void ProductService_GetById_ValidProductReturnsProduct() {
        // Arrange
        int productId = 1;
        Product product = new Product(1, "Test Product", 10.0, "thumbnail", "Test Detail", new Category(), 100, new Date());
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        ProductDTO productDTO = productService.getProductById(productId);

        // Assert
        assertNotNull(productDTO);
        assertEquals(product.getId(), productDTO.getId());
        assertEquals(product.getName(), productDTO.getName());
    }

    @Test
    public void ProductService_GetById_ProductNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(productId));
    }

    @Test
    public void ProductService_SaveProduct_ValidProductDTO_ReturnsSavedProductDTO() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setPrice(10.0);
        Product product = productModelMapper.map(productDTO, Product.class);
        when(productRepository.save(product)).thenReturn(product);

        // Act
        ProductDTO savedProductDTO = productService.saveProduct(productDTO);

        // Assert
        assertNotNull(savedProductDTO);
        assertEquals(product.getId(), savedProductDTO.getId());
        assertEquals(product.getName(), savedProductDTO.getName());
    }

    @Test
    public void ProductService_SaveProduct_DuplicateProductNameThrowsIllegalArgumentException() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setPrice(10.0);

        when(productRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.saveProduct(productDTO));
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
        ProductDTO updatedProductDTO = productService.updateProduct(productId, updatedName, updatedPrice, updatedThumbnail, updatedDetail, categoryId, newQuantity, newDate);

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
        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(productId, updatedName, updatedPrice, updatedThumbnail, updatedDetail, categoryId, newQuantity, newDate));
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
        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(productId, updatedName, updatedPrice, updatedThumbnail, updatedDetail, categoryId, newQuantity, newDate));
    }

    @Test
    public void ProductService_Delete_ValidProductId_DeletesProduct() {
        // Arrange
        int productId = 1;
        Product existingProduct = new Product(productId, "Test Product", 10.0, "thumbnail", "Test Details", new Category(), 100, new Date());
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        // Act
        assertDoesNotThrow(() -> productService.deleteProduct(productId));

        // Assert
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    public void ProductService_Delete_ProductNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(productId));
    }

    @Test
    public void ProductService_Delete_AssociatedProductThrowsIllegalArgumentException() {
        // Arrange
        int productId = 1;
        Product existingProduct = new Product(productId, "Test Product", 10.0, "thumbnail", "Test Details", new Category(), 100, new Date());
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(productId);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.deleteProduct(productId));
    }
}
