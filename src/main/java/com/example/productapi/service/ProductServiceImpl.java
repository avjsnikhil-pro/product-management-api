package com.example.productapi.service;

import com.example.productapi.dto.*;
import com.example.productapi.entity.Product;
import com.example.productapi.exception.ResourceNotFoundException;
import com.example.productapi.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto createProduct(final ProductCreateDto dto) {
        if (productRepository.existsByName(dto.name())) {
            throw new IllegalArgumentException("Product with this name already exists");
        }
        
        final Product product = new Product();
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setQuantity(dto.quantity());
        product.setCategory(dto.category());
        
        final Product saved = productRepository.save(product);
        return mapToDto(saved);
    }

    @Override
    public ProductDto getProductById(final Long id) {
        final Product product = findProductOrThrow(id);
        return mapToDto(product);
    }

    @Override
    public Page<ProductDto> getAllProducts(final String category, final BigDecimal minPrice, final BigDecimal maxPrice, final Pageable pageable) {
        final Specification<Product> spec = (root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if (category != null && !category.isEmpty()) {
                predicates.add(cb.equal(root.get("category"), category));
            }
            if (minPrice != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return productRepository.findAll(spec, pageable).map(this::mapToDto);
    }

    @Override
    public ProductDto updateProduct(final Long id, final ProductUpdateDto dto, final boolean isPartial) {
        final Product product = findProductOrThrow(id);

        if (dto.name() != null) product.setName(dto.name());
        else if (!isPartial) throw new IllegalArgumentException("Name is required for full update");

        if (dto.price() != null) product.setPrice(dto.price());
        else if (!isPartial) throw new IllegalArgumentException("Price is required for full update");

        if (dto.quantity() != null) product.setQuantity(dto.quantity());
        else if (!isPartial) throw new IllegalArgumentException("Quantity is required for full update");

        if (dto.category() != null) product.setCategory(dto.category());
        else if (!isPartial) throw new IllegalArgumentException("Category is required for full update");

        return mapToDto(productRepository.save(product));
    }

    @Override
    public void deleteProduct(final Long id) {
        final Product product = findProductOrThrow(id);
        product.setDeleted(true); 
        productRepository.save(product);
    }

    private Product findProductOrThrow(final Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    private ProductDto mapToDto(final Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getQuantity(), product.getCategory());
    }
}