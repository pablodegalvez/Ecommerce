package com.online.tienda.ecommerce.mapper;


import com.online.tienda.ecommerce.dto.ProductRequestDto;
import com.online.tienda.ecommerce.dto.ProductResponseDto;
import com.online.tienda.ecommerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {


    @Mapping(target = "categories", ignore = true)
    Product toEntity(ProductRequestDto productRequestDto);

    ProductResponseDto toDto(Product product);

}
