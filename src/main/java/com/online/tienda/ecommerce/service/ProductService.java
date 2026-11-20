package com.online.tienda.ecommerce.service;


import com.online.tienda.ecommerce.dto.ProductRequestDto;
import com.online.tienda.ecommerce.dto.ProductResponseDto;
import com.online.tienda.ecommerce.entity.Category;
import com.online.tienda.ecommerce.entity.Product;
import com.online.tienda.ecommerce.exception.NotFoundResourceException;
import com.online.tienda.ecommerce.mapper.ProductMapper;
import com.online.tienda.ecommerce.repository.CategoryRepository;
import com.online.tienda.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundResourceException("Product not found"));

        if(!product.getActive()){
            throw new NotFoundResourceException("Product is not available");
        }
        return product;
    }

    @Transactional(readOnly = true)
    public ProductResponseDto findByIdDto(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundResourceException("Product not found"));

        if(!product.getActive()){
            throw new NotFoundResourceException("Product is not available");
        }
        return productMapper.toDto(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> findByName(String productName, Pageable pageable) {
        Page<Product> products = productRepository.findByNameAndActiveTrue(productName, pageable);

        return products.map(productMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> findAllByActiveTrue(Pageable pageable) {
        Page<Product> products = productRepository.findAllActiveProduct(pageable);
        return products.map(productMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> findAllByPriceGreaterThan(BigDecimal price, Pageable pageable) {
        Page<Product> products = productRepository.findAllByPriceGreaterThanEqual(price, pageable);
        return products.map(productMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> findAllByPriceLessThan(BigDecimal price, Pageable pageable) {
        Page<Product> products = productRepository.findAllByPriceLessThanEqual(price, pageable);
        return products.map(productMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> findAllByPriceBetween(BigDecimal priceMax, BigDecimal priceMin, Pageable pageable) {
        Page<Product> products = productRepository.findAllByPriceBetween(priceMax, priceMin, pageable);
        return products.map(productMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> findByCategory(Long categoryId, Pageable pageable) {
        Page<Product> products = productRepository.findByCategory(categoryId, pageable);
        return products.map(productMapper::toDto);
    }

    @Transactional
    public void saveProduct (ProductRequestDto productRequestDto) {

        Product product = productMapper.toEntity(productRequestDto);

        if(productRequestDto.categoryIds() != null && !productRequestDto.categoryIds().isEmpty()){
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(productRequestDto.categoryIds()));
            product.setCategories(categories);
        }
        product.setActive(true);
        product.setId(null);
        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct (Long id) {
        Product existingProduct = findById(id);
        existingProduct.setActive(false);
        productRepository.save(existingProduct);
    }

    @Transactional
    public void updateProduct (Long id, ProductRequestDto productRequestDto) {
        Product existingProduct = findById(id);

        existingProduct.setName(productRequestDto.name());
        existingProduct.setPrice(productRequestDto.price());
        existingProduct.setDescription(productRequestDto.description());
        //existingProduct.setActive(); TENGO QUE MIRAR SI COMPENSA AÑADIR AL PRODUCTREQUESTDTO EL ATRIBUTO ACTIVE. Preguntar si compensa devolver algo aquí o usar pruebas unitarias. Poner comentarios a cada servicio.
        existingProduct.setStock(productRequestDto.stock());
        existingProduct.setCategories(categoryRepository.findAllById(productRequestDto.categoryIds()));
        productRepository.save(existingProduct);
    }


}
