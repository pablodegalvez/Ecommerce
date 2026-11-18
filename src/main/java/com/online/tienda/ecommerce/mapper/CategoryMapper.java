package com.online.tienda.ecommerce.mapper;


import com.online.tienda.ecommerce.dto.CategoryResponseDto;
import com.online.tienda.ecommerce.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDto categoryToCategoryDto(Category category);
}
