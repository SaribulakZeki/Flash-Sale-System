package com.project.flashsalesystem.service;

import com.project.flashsalesystem.entity.Product;
import com.project.flashsalesystem.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String PRODUCT_CACHE_KEY = "all_products";

    public List<Product> getAllProducts() {
        @SuppressWarnings("unchecked")
        List<Product> cachedProducts = (List<Product>) redisTemplate.opsForValue().get(PRODUCT_CACHE_KEY);

        if (cachedProducts != null) {
            return cachedProducts;
        }

        List<Product> products = productRepository.findAll();
        redisTemplate.opsForValue().set(PRODUCT_CACHE_KEY, products, 10, TimeUnit.MINUTES);
        return products;
    }

    public Product createProduct(Product product) {
        Product newProduct = productRepository.save(product);
        redisTemplate.delete(PRODUCT_CACHE_KEY);
        return newProduct;
    }
}