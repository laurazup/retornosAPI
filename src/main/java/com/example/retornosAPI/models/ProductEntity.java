package com.example.retornosAPI.models;

import com.example.retornosAPI.enums.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String name;

    @Size(max = 500, message = "A descrição pode ter no máximo 500 caracteres")
    private String description;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que 0")
    private Double price;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @Min(value = 0, message = "A quantidade em estoque deve ser maior ou igual a 0")
    private Integer stock;

    @NotNull(message = "A categoria é obrigatória")
    @Enumerated(EnumType.STRING)
    private Category category;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    // Construtores
}