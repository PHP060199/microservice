package com.example.productservice.service.impl;


import com.example.productservice.domain.Product;
import com.example.productservice.dto.ProductDTO;
import com.example.productservice.exception.CustomException;
import com.example.productservice.exception.define.ErrorCode;
import com.example.productservice.exception.define.ErrorMessage;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .build();
        productRepository.save(product);
        return this.mapToProductDTO(product);
    }

    @Override
    public ProductDTO updateProductById(ProductDTO productDTO) {
        Optional<Product> product = productRepository.getProductById(productDTO.getId());
        if (product.isEmpty())
            throw new CustomException(ErrorMessage.PRODUCT_NOT_FOUND, ErrorCode.notFound);
        Product newProduct = product.get().builder().id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .build();
        productRepository.save(newProduct);
        return this.mapToProductDTO(newProduct);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapToProductDTO).toList();
    }

    @Override
    public ProductDTO getProductById(String id) {
        Optional<Product> product = productRepository.getProductById(id);
        if (product.isEmpty()) {
            throw new CustomException(ErrorMessage.PRODUCT_NOT_FOUND, ErrorCode.notFound);
        }
        return this.mapToProductDTO(product.get());
    }

    @Override
    public List<ProductDTO> findProductByCondition(String name, Float price) {
        List<Product> productList = productRepository.getProductsByNameOrPrice(name, price);
        return productList.stream().map(this::mapToProductDTO).toList();
    }

    @Override
    public ProductDTO deleteProductById(String id) {
        Optional<Product> product = productRepository.getProductById(id);
        if (product.isEmpty())
            throw new CustomException(ErrorMessage.PRODUCT_NOT_FOUND, ErrorCode.notFound);
        productRepository.delete(product.get());
        return this.mapToProductDTO(product.get());
    }

    private ProductDTO mapToProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
