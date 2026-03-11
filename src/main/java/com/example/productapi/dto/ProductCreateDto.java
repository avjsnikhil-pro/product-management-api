package com.example.productapi.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductCreateDto(
    @NotBlank(message = "Name is required") 
    String name,

    @NotNull(message = "Price is required") 
    @Positive(message = "Price must be strictly positive") 
    BigDecimal price,

    @NotNull(message = "Quantity is required") 
    @Min(value = 0, message = "Quantity cannot be negative") 
    Integer quantity,

    @NotBlank(message = "Category is required") 
    String category
) {}