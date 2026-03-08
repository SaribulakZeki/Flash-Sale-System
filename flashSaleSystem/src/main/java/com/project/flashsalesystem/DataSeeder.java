package com.project.flashsalesystem;

import com.project.flashsalesystem.entity.Product;
import com.project.flashsalesystem.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            Product p1 = Product.builder()
                    .name("Tarkan Harbiye Konseri")
                    .description("Megastar sahnede!")
                    .price(BigDecimal.valueOf(500))
                    .quantity(100)
                    .build();

            Product p2 = Product.builder()
                    .name("Fenerbahçe - Galatasaray Derbisi")
                    .description("Kale arkası biletleri")
                    .price(BigDecimal.valueOf(1000))
                    .quantity(50)
                    .build();

            productRepository.saveAll(Arrays.asList(p1, p2));
        }
    }
}