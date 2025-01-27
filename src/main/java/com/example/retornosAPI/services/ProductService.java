package com.example.retornosAPI.services;

import com.example.retornosAPI.exceptions.InvalidProductException;
import com.example.retornosAPI.exceptions.ProductNotFoundException;
import com.example.retornosAPI.models.ProductEntity;
import com.example.retornosAPI.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductEntity createProduct(ProductEntity product) {
        validateProduct(product);
        return repository.save(product);
    }

    public ProductEntity getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto com ID " + id + " não encontrado"));
    }

    public List<ProductEntity> getAllProducts() {
        return repository.findAll();
    }

    public void deleteProduct(Long id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException("Produto com ID " + id + " não encontrado");
        }
        repository.deleteById(id);
    }

    public ProductEntity updateProduct(Long id, ProductEntity updatedProduct) {
        validateProduct(updatedProduct);
        ProductEntity existingProduct = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto com ID " + id + " não encontrado"));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStock(updatedProduct.getStock());
        existingProduct.setCategory(updatedProduct.getCategory());

        return repository.save(existingProduct);
    }

    private void validateProduct(ProductEntity product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new InvalidProductException("O nome do produto é obrigatório.");
        }
        if (product.getName().length() < 3 || product.getName().length() > 100) {
            throw new InvalidProductException("O nome do produto deve ter entre 3 e 100 caracteres.");
        }
        if (product.getDescription() != null && product.getDescription().length() > 500) {
            throw new InvalidProductException("A descrição pode ter no máximo 500 caracteres.");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new InvalidProductException("O preço do produto deve ser maior que zero.");
        }
        if (product.getStock() == null || product.getStock() < 0) {
            throw new InvalidProductException("A quantidade em estoque deve ser maior ou igual a zero.");
        }
        if (product.getCategory() == null) {
            throw new InvalidProductException("A categoria do produto é obrigatória.");
        }
    }
}