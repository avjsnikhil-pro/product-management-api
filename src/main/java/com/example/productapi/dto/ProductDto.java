package com.example.productapi.dto;

import java.math.BigDecimal;

public record ProductDto(
    Long id, 
    String name, 
    BigDecimal price, 
    Integer quantity, 
    String category
) {}