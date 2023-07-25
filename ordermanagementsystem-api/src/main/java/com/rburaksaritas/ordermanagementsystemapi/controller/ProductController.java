package com.rburaksaritas.ordermanagementsystemapi.controller;

import com.rburaksaritas.ordermanagementsystemapi.dto.ProductDTO;
import com.rburaksaritas.ordermanagementsystemapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    // TODO: Implement controller methods based on tests
    @GetMapping("/get")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return null;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) {
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        return null;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Integer id,
            @RequestParam String updatedName,
            @RequestParam Double updatedPrice,
            @RequestParam String updatedThumbnail,
            @RequestParam String updatedDetail,
            @RequestParam Integer updatedCategoryId,
            @RequestParam Integer newQuantity,
            @RequestParam Date newDate
    ) {
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        return null;
    }
}
