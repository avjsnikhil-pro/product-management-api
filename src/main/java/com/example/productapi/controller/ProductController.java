package com.example.productapi.controller;

import com.example.productapi.dto.*;
import com.example.productapi.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management API", description = "Endpoints for managing products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Create a new product")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody final ProductCreateDto dto) {
        return new ResponseEntity<>(productService.createProduct(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by its ID")
    public ResponseEntity<ProductDto> getProductById(@PathVariable final Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    @Operation(summary = "Get all products with pagination, sorting, and filtering")
    public ResponseEntity<Page<ProductDto>> getAllProducts(
            @RequestParam(required = false) final String category,
            @RequestParam(required = false) final BigDecimal minPrice,
            @RequestParam(required = false) final BigDecimal maxPrice,
            final Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(category, minPrice, maxPrice, pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Fully update a product")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable final Long id, 
            @Valid @RequestBody final ProductUpdateDto dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto, false));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update a product")
    public ResponseEntity<ProductDto> patchProduct(
            @PathVariable final Long id, 
            @RequestBody final ProductUpdateDto dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto, true));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete a product")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}