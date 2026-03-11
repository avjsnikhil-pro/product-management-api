package com.example.productapi.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductUpdateDto(
    String name,

    @Positive(message = "Price must be strictly positive") 
    BigDecimal price,

    @Min(value = 0, message = "Quantity cannot be negative") 
    Integer quantity,

    String category
) {}