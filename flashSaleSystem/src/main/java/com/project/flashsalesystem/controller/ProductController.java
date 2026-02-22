package com.project.flashsalesystem.controller;

import com.project.flashsalesystem.entity.Product;
import com.project.flashsalesystem.service.ProductService;
import com.project.flashsalesystem.service.OrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final OrderProducer orderProducer;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @PostMapping("/{id}/buy")
    public ResponseEntity<String> buyProduct(@PathVariable Long id) {
        orderProducer.sendOrderEvent(id);
        return ResponseEntity.ok("Satın alma talebiniz alındı! İşleniyor...");
    }
}
