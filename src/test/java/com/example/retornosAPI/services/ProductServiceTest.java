package com.example.retornosAPI.services;

import com.example.retornosAPI.models.Product;
import com.example.retornosAPI.models.ProductEntity;
import com.example.retornosAPI.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct_Success() {
        // Arrange
        Product product = new Product(null, "Smartphone", 1500.0, 10, "Eletrônicos");
        ProductEntity savedEntity = new ProductEntity(1L, "Smartphone", 1500.0, 10, "Eletrônicos");

        when(repository.save(any(ProductEntity.class))).thenReturn(savedEntity);

        // Act
        Product createdProduct = service.createProduct(product);
       // Assert
        assertNotNull(createdProduct);
        assertEquals(1L, createdProduct.id());
        assertEquals("Smartphone", createdProduct.name());
        assertEquals(1500.0, createdProduct.price());
        assertEquals(10, createdProduct.stock());
        assertEquals("Eletrônicos", createdProduct.category());

        verify(repository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    public void testCreateProduct_MissingStock_ThrowsException() {
        // Arrange
        Product product = new Product(null, "Smartphone", 1500.0, null, "Eletrônicos");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.createProduct(product);
        });

        assertEquals("Os campos 'stock' e 'category' são obrigatórios.", exception.getMessage());
        verify(repository, never()).save(any(ProductEntity.class));
    }

    @Test
    public void testCreateProduct_MissingCategory_ThrowsException() {
        // Arrange
        Product product = new Product(null, "Smartphone", 1500.0, 10, null);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.createProduct(product);
        });

        assertEquals("Os campos 'stock' e 'category' são obrigatórios.", exception.getMessage());
        verify(repository, never()).save(any(ProductEntity.class));
    }
}