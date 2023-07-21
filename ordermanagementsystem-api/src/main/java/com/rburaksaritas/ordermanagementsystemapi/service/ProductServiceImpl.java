package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.ProductDTO;
import com.rburaksaritas.ordermanagementsystemapi.model.Category;
import com.rburaksaritas.ordermanagementsystemapi.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper){
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return null;
    }

    @Override
    public ProductDTO getProductById(Integer id) {
        return null;
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        return null;
    }

    @Override
    public ProductDTO updateProduct(Integer id, String updatedName,
                                    String updatedPrice, String updatedThumbnail,
                                    String updatedDetail, Integer updatedCategoryId,
                                    Integer newQuantity, Date newDate) {
        return null;
    }

    @Override
    public void deleteProduct(Integer id) {}
}
