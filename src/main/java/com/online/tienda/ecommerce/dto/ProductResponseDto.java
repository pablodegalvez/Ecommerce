package com.online.tienda.ecommerce.dto;


import java.math.BigDecimal;
import java.util.Set;

public record ProductResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        Set<CategoryResponseDto> categories
) {
}
