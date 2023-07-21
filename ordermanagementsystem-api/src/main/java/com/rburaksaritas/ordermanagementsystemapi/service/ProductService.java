package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.ProductDTO;
import com.rburaksaritas.ordermanagementsystemapi.model.Category;

import java.util.Date;
import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Integer id);
    ProductDTO saveProduct(ProductDTO productDTO);
    public ProductDTO updateProduct(Integer id, String updatedName,
                                    Double updatedPrice, String updatedThumbnail,
                                    String updatedDetail, Integer updatedCategoryId,
                                    Integer newQuantity, Date newDate);
    void deleteProduct(Integer id);
}
