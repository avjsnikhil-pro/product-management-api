package com.example.productapi.service;

import com.example.productapi.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;

public interface ProductService {
    ProductDto createProduct(ProductCreateDto dto);
    ProductDto getProductById(Long id);
    Page<ProductDto> getAllProducts(String category, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    ProductDto updateProduct(Long id, ProductUpdateDto dto, boolean isPartial);
    void deleteProduct(Long id);
}