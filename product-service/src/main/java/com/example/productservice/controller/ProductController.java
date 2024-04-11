package com.example.productservice.controller;

import com.example.productservice.dto.ProductDTO;
import com.example.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO creteProduct(@RequestBody ProductDTO productRequest) {
         return productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> getAllProduct() {
       return productService.getAllProducts();
    }

    @GetMapping(value = "/getByCondition")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> getProductsByCondition(@RequestParam (name = "name") String name,
                                                   @RequestParam (name = "price") Float price) {
        return productService.findProductByCondition(name, price);
    }

    @GetMapping(value = "/getById")
    public ResponseEntity<?> getProductById(@RequestParam String id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO updateProductById(@RequestBody ProductDTO productDTO) {
        return productService.updateProductById(productDTO);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO deleteProductById(@RequestParam String id) {
        return productService.deleteProductById(id);
    }
}
