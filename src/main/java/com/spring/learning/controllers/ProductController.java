package com.spring.learning.controllers;

import com.spring.learning.dto.shared.ApiResponseDto;
import com.spring.learning.entities.Product;
import com.spring.learning.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public List<Product> products(@RequestParam(value = "title", required = false) String title) {
        log.info("Attempting to get products");

        try {
            return productService.listProducts(title);
        } catch (Exception e) {
            log.error("Unexpected error during loading products {}", e.getMessage());
        }

        return List.of();
    }

    @PostMapping("/products")
    public ResponseEntity<ApiResponseDto> createProduct(@RequestBody Product product) {
        log.info("Attempting to create product with name: {}", product.getTitle());

        try {
            productService.saveProduct(product);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponseDto("Product created successfully", true));
        } catch (Exception e) {
            log.error("Unexpected error during creation product {}: {}", product.getTitle(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto("Something went wrong", false));
        }
    }

    @GetMapping("/products/{id}")
    public Product productInfo(@PathVariable("id") long id) {
        log.info("Attempting to get product with id={}", id);

        try {
            return productService.getProductById(id);
        } catch (Exception e) {
            log.error("Unexpected error during loading product with id={}, {}", id, e.getMessage());
        }

        return null;
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ApiResponseDto> createProduct(@PathVariable("id") long id) {
        log.info("Attempting to delete product with id: {}", id);

        try {
            productService.deleteProduct(id);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseDto("Product deleted successfully", true));
        } catch (Exception e) {
            log.error("Unexpected error during deletion of product with id={}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto("Something went wrong", false));
        }
    }
}
