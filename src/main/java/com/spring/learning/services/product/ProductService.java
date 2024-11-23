package com.spring.learning.services.product;

import com.spring.learning.entities.Product;
import com.spring.learning.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> listProducts(String title) {
        if(title != null) return productRepository.findByTitle(title);

        return productRepository.findAll();
    }

    public void saveProduct(Product product) {
        log.info("Saving new {}", product);

        productRepository.save(product);
    }

    public void deleteProduct(long id) {
        log.info("Deleting {}", id);

        productRepository.deleteById(id);
    }

    public Product getProductById(long id) {
        return productRepository.findById(id).orElse(null);
    }
}
