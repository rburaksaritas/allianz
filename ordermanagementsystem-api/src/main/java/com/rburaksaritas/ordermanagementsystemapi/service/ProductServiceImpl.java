package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.ProductDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.model.Category;
import com.rburaksaritas.ordermanagementsystemapi.model.Product;
import com.rburaksaritas.ordermanagementsystemapi.repository.CategoryRepository;
import com.rburaksaritas.ordermanagementsystemapi.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ModelMapper modelMapper){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        try {
            Product product = modelMapper.map(productDTO, Product.class);
            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Product name must be unique.");
        }
    }

    @Override
    public ProductDTO updateProduct(Integer id, String updatedName,
                                    Double updatedPrice, String updatedThumbnail,
                                    String updatedDetail, Integer updatedCategoryId,
                                    Integer newQuantity, Date newDate) {
        // Check if the product exists.
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        // Check if the category exists.
        Category category = categoryRepository.findById(updatedCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", updatedCategoryId));

        try {
            // TODO: fix NullPointerException when product itself is modified and mapped
            // Create a new product for updated product
            Product updatedProduct = new Product();
            // Update fields
            updatedProduct.setId(id);
            updatedProduct.setName(updatedName);
            updatedProduct.setPrice(updatedPrice);
            updatedProduct.setThumbnail(updatedThumbnail);
            updatedProduct.setDetails(updatedDetail);
            updatedProduct.setCategory(category);
            updatedProduct.setQuantity(newQuantity);
            updatedProduct.setTimestamp(newDate);

            // Save the updated product
            productRepository.save(updatedProduct);
            return modelMapper.map(updatedProduct, ProductDTO.class);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Product name must be unique.");
        }
    }

    @Override
    public void deleteProduct(Integer id) {
        // Check if the product exists.
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Associated data prevents deletion. Cannot delete the product.");
        }
    }

}
