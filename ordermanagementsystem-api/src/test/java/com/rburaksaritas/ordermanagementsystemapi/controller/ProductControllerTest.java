package com.rburaksaritas.ordermanagementsystemapi.controller;

import com.rburaksaritas.ordermanagementsystemapi.dto.CategoryDTO;
import com.rburaksaritas.ordermanagementsystemapi.dto.ProductDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.service.ProductService;
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

class ProductControllerTests {

    private ProductController productController;
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
    }

    @Test
    void getAllProducts_ReturnsAllProductsSuccessfully() {
        // Arrange
        List<ProductDTO> expectedProducts = new ArrayList<>();
        expectedProducts.add(new ProductDTO(1, "Product 1", 100.0, "thumbnail1", "detail1", new CategoryDTO(), 100, new Date()));
        expectedProducts.add(new ProductDTO(2, "Product 2", 200.0, "thumbnail2", "detail2", new CategoryDTO(), 200, new Date()));
        when(productService.getAllProducts()).thenReturn(expectedProducts);

        // Act
        ResponseEntity<List<ProductDTO>> responseEntity = productController.getAllProducts();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedProducts.size(), responseEntity.getBody().size());
    }

    @Test
    void getProductById_ValidProductId_ReturnsProductSuccessfully() {
        // Arrange
        int productId = 1;
        ProductDTO expectedProduct = new ProductDTO(productId, "Test Product", 100.0, "testthumbnail", "testdetail", new CategoryDTO(), 50, new Date());
        when(productService.getProductById(productId)).thenReturn(expectedProduct);

        // Act
        ResponseEntity<ProductDTO> responseEntity = productController.getProductById(productId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedProduct.getId(), responseEntity.getBody().getId());
        assertEquals(expectedProduct.getName(), responseEntity.getBody().getName());
    }

    @Test
    void getProductById_ProductNotFound_ReturnsNotFound() {
        // Arrange
        int productId = 1;
        when(productService.getProductById(productId)).thenReturn(null);

        // Act
        ResponseEntity<ProductDTO> responseEntity = productController.getProductById(productId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void addProduct_ValidProduct_ReturnsCreatedProduct() {
        // Arrange
        ProductDTO newProduct = new ProductDTO(null, "New Product", 500.0, "newthumbnail", "newdetail", new CategoryDTO(), 10, new Date());
        ProductDTO expectedSavedProduct = new ProductDTO(1, "New Product", 500.0, "newthumbnail", "newdetail", new CategoryDTO(), 10, new Date());
        when(productService.saveProduct(newProduct)).thenReturn(expectedSavedProduct);

        // Act
        ResponseEntity<ProductDTO> responseEntity = productController.addProduct(newProduct);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedSavedProduct.getId(), responseEntity.getBody().getId());
        assertEquals(expectedSavedProduct.getName(), responseEntity.getBody().getName());
    }

    @Test
    void addProduct_DuplicateProductName_ReturnsBadRequest() {
        // Arrange
        ProductDTO newProduct = new ProductDTO(null, "Duplicate Product", 500.0, "newthumbnail", "newdetail", new CategoryDTO(), 10, new Date());
        when(productService.saveProduct(newProduct)).thenThrow(new IllegalArgumentException("Product name must be unique."));

        // Act
        ResponseEntity<ProductDTO> responseEntity = productController.addProduct(newProduct);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void updateProduct_ValidProduct_ReturnsUpdatedProduct() {
        // Arrange
        int productId = 1;
        ProductDTO updatedProduct = new ProductDTO(productId, "Updated Product", 800.0, "updatedthumbnail", "updateddetail", new CategoryDTO(), 20, new Date());
        when(productService.updateProduct(productId, updatedProduct.getName(), updatedProduct.getPrice(), updatedProduct.getThumbnail(), updatedProduct.getDetails(), updatedProduct.getCategory().getId(), updatedProduct.getQuantity(), updatedProduct.getTimestamp()))
                .thenReturn(updatedProduct);

        // Act
        ResponseEntity<ProductDTO> responseEntity = productController.updateProduct(productId, updatedProduct.getName(), updatedProduct.getPrice(), updatedProduct.getThumbnail(), updatedProduct.getDetails(), updatedProduct.getCategory().getId(), updatedProduct.getQuantity(), updatedProduct.getTimestamp());

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updatedProduct.getId(), responseEntity.getBody().getId());
        assertEquals(updatedProduct.getName(), responseEntity.getBody().getName());
        assertEquals(updatedProduct.getPrice(), responseEntity.getBody().getPrice());
    }

    @Test
    void updateProduct_ProductNotFound_ReturnsNotFound() {
        // Arrange
        int productId = 1;
        ProductDTO updatedProduct = new ProductDTO(productId, "Updated Product", 800.0, "updatedthumbnail", "updateddetail", new CategoryDTO(), 20, new Date());
        when(productService.updateProduct(productId, updatedProduct.getName(), updatedProduct.getPrice(), updatedProduct.getThumbnail(), updatedProduct.getDetails(), updatedProduct.getCategory().getId(), updatedProduct.getQuantity(), updatedProduct.getTimestamp()))
                .thenThrow(new ResourceNotFoundException("Product", "id", productId));

        // Act
        ResponseEntity<ProductDTO> responseEntity = productController.updateProduct(productId, updatedProduct.getName(), updatedProduct.getPrice(), updatedProduct.getThumbnail(), updatedProduct.getDetails(), updatedProduct.getCategory().getId(), updatedProduct.getQuantity(), updatedProduct.getTimestamp());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void updateProduct_DuplicateProductName_ReturnsBadRequest() {
        // Arrange
        int productId = 1;
        ProductDTO updatedProduct = new ProductDTO(productId, "Updated Product", 800.0, "updatedthumbnail", "updateddetail", new CategoryDTO(), 20, new Date());
        when(productService.updateProduct(productId, updatedProduct.getName(), updatedProduct.getPrice(), updatedProduct.getThumbnail(), updatedProduct.getDetails(), updatedProduct.getCategory().getId(), updatedProduct.getQuantity(), updatedProduct.getTimestamp()))
                .thenThrow(new IllegalArgumentException("Product name must be unique."));

        // Act
        ResponseEntity<ProductDTO> responseEntity = productController.updateProduct(productId, updatedProduct.getName(), updatedProduct.getPrice(), updatedProduct.getThumbnail(), updatedProduct.getDetails(), updatedProduct.getCategory().getId(), updatedProduct.getQuantity(), updatedProduct.getTimestamp());

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void deleteProduct_ValidProductId_ReturnsNoContent() {
        // Arrange
        int productId = 1;

        // Act
        ResponseEntity<Void> responseEntity = productController.deleteProduct(productId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteProduct_ProductNotFound_ReturnsNotFound() {
        // Arrange
        int productId = 1;
        doThrow(new ResourceNotFoundException("Product", "id", productId)).when(productService).deleteProduct(productId);

        // Act
        ResponseEntity<Void> responseEntity = productController.deleteProduct(productId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
