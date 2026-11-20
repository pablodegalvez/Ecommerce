package com.online.tienda.ecommerce.controller;


import com.online.tienda.ecommerce.dto.ProductRequestDto;
import com.online.tienda.ecommerce.dto.ProductResponseDto;
import com.online.tienda.ecommerce.entity.Product;
import com.online.tienda.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @PageableDefault(page = 0, size = 10, sort = "name") Pageable pageable
    ) {
        if(name != null) {
            return ResponseEntity.ok(productService.findByName(name, pageable));
        }

        if(categoryId != null) {
            return ResponseEntity.ok(productService.findByCategory(categoryId, pageable));
        }

        if (minPrice != null && maxPrice != null) {
            return ResponseEntity.ok(productService.findAllByPriceBetween(minPrice, maxPrice, pageable));
        }

        if (minPrice != null) {
            return ResponseEntity.ok(productService.findAllByPriceGreaterThan(minPrice, pageable));
        }

        if (maxPrice != null) {
            return ResponseEntity.ok(productService.findAllByPriceLessThan(maxPrice, pageable));
        }

        return ResponseEntity.ok(productService.findAllByActiveTrue(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findByIdDto(id));
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        productService.saveProduct(productRequestDto);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@Valid @PathVariable Long id, @RequestBody ProductRequestDto productRequestDto) {
        productService.updateProduct(id, productRequestDto);
        return  ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }





}
