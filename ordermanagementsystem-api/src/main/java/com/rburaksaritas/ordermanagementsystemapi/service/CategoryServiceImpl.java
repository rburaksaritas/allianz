package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.CategoryDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.model.Category;
import com.rburaksaritas.ordermanagementsystemapi.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        try {
            Category category = modelMapper.map(categoryDTO, Category.class);
            Category savedCategory = categoryRepository.save(category);
            return modelMapper.map(savedCategory, CategoryDTO.class);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Category name must be unique.");
        }
    }

    @Override
    public CategoryDTO updateCategory(Integer id, String updatedName, String updatedDetails, Date updatedTimestamp) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        try {
            // Update the category fields with the new values
            category.setName(updatedName);
            category.setDetails(updatedDetails);
            category.setTimestamp(updatedTimestamp);

            // Save the updated category
            Category updatedCategory = categoryRepository.save(category);
            return modelMapper.map(updatedCategory, CategoryDTO.class);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Category name must be unique.");
        }
    }

    @Override
    public void deleteCategory(Integer id) {
        // Check if the category exists before attempting to delete
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        try {
            // Attempt to delete the category
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            // If the category is associated with existing products, throw an IllegalArgumentException
            throw new IllegalArgumentException("Category with id " + id + " is associated with existing products and cannot be deleted.");
        }
    }

}
