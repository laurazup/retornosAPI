package com.example.retornosAPI.models;

import com.example.retornosAPI.enums.Category;

public record Product(Long id, String name, Double price, Integer stock, Category category) {

}