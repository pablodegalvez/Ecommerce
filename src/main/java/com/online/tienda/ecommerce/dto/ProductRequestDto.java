package com.online.tienda.ecommerce.dto;

import com.online.tienda.ecommerce.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.Set;

public record ProductRequestDto(
        @NotBlank(message = "The name is necessary")
        String name,

        String description,

        @NotNull(message = "Price is mandatory")
        @Positive(message = "Price must be positive")
        BigDecimal price,

        @NotNull(message = "Stock is mandatory")
        @PositiveOrZero(message = "Stock must be zero or greater")
        Integer stock,

        Boolean active,

        Set<Long> categoryIds
) {
}
