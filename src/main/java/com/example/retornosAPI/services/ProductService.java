package com.example.retornosAPI.services;

import com.example.retornosAPI.dtos.ProductRequestDTO;
import com.example.retornosAPI.exceptions.ResourceNotFoundException;
import com.example.retornosAPI.models.Product;
import com.example.retornosAPI.models.ProductEntity;
import com.example.retornosAPI.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product createProduct(ProductRequestDTO product) {
        ProductEntity entity = new ProductEntity(null, product.getName(), product.getPrice());
        ProductEntity savedEntity = repository.save(entity);
        return new Product(savedEntity.getId(), savedEntity.getName(), savedEntity.getPrice());
    }

    public Product getProductById(Long id) {
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return new Product(entity.getId(), entity.getName(), entity.getPrice());
    }

    public List<Product> getAllProducts() {
        return repository.findAll().stream()
                .map(entity -> new Product(entity.getId(), entity.getName(), entity.getPrice()))
                .collect(Collectors.toList());
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

    // Atualizar um produto existente
    public Product updateProduct(Long id, Product updatedProduct) {
        // Verificar se o produto existe
        ProductEntity existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with ID " + id + " not found"));

        // Atualizar os dados do produto
        existingEntity.setName(updatedProduct.name());
        existingEntity.setPrice(updatedProduct.price());

        // Salvar as alterações no banco de dados
        ProductEntity savedEntity = repository.save(existingEntity);

        // Retornar o produto atualizado
        return new Product(savedEntity.getId(), savedEntity.getName(), savedEntity.getPrice());
    }

    // Buscar produtos pelo nome
    public List<Product> getProductsByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("O nome do produto não pode ser vazio.");
        }

        List<ProductEntity> entities = repository.findByNameContainingIgnoreCase(name);
        if (entities.isEmpty()) {
//            System.out.println("Nenhum produto encontrado com o nome: " + name);
            logger.warn("Nenhum produto encontrado com o nome: " + name);
        } else {
//            System.out.println("Produtos encontrados com o nome '" + name + "': " + entities.size());
            logger.info("Produtos encontrados com o nome '" + name + "': " + entities.size());
        }
        return entities.stream()
                .map(entity -> new Product(entity.getId(), entity.getName(), entity.getPrice()))
                .collect(Collectors.toList());
    }
}