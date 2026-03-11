package com.example.productapi.service;

import com.example.productapi.dto.ProductCreateDto;
import com.example.productapi.entity.Product;
import com.example.productapi.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void createProduct_Success() {
        ProductCreateDto dto = new ProductCreateDto("Laptop", BigDecimal.valueOf(1000), 5, "Electronics");
        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("Laptop");
        savedProduct.setPrice(BigDecimal.valueOf(1000));
        savedProduct.setQuantity(5);
        savedProduct.setCategory("Electronics");
        savedProduct.setDeleted(false);

        when(productRepository.existsByName(dto.name())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        var result = productService.createProduct(dto);

        assertNotNull(result);
        assertEquals("Laptop", result.name());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void createProduct_DuplicateNameThrowsException() {
        ProductCreateDto dto = new ProductCreateDto("Laptop", BigDecimal.valueOf(1000), 5, "Electronics");
        
        when(productRepository.existsByName(dto.name())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(dto));
        verify(productRepository, never()).save(any(Product.class));
    }
}