package com.example.retornosAPI.controllers;

import com.example.retornosAPI.dtos.ProductRequestDTO;
import com.example.retornosAPI.models.Product;
import com.example.retornosAPI.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    public ProductService service;

    @PostMapping("teste")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequestDTO product) {
        return ResponseEntity.ok(service.createProduct(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}