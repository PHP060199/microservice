package com.example.productservice.service;

import com.example.productservice.dto.ProductDTO;
import java.util.List;


public interface ProductService {

    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProductById(ProductDTO productDTO);

    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(String id);
    List<ProductDTO> findProductByCondition(String name, Float price);
    ProductDTO deleteProductById(String id);
}
