package com.online.tienda.ecommerce.service;


import com.online.tienda.ecommerce.entity.Product;
import com.online.tienda.ecommerce.exception.NotFoundResourceException;
import com.online.tienda.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundResourceException("Product not found"));

        if(!product.getActive()){
            throw new NotFoundResourceException("Product is not available");
        }
        return product;
    }

    public Optional<Product> findByName(String productName) {
        return productRepository.findByNameAndActiveTrue(productName);
    }

    public Page<Product> findAllByActiveTrue(Pageable pageable) {
        return productRepository.findAllActiveProduct(pageable);
    }

    public Page<Product> findAllByPriceGreaterThan(BigDecimal price, Pageable pageable) {
        return productRepository.findAllByPriceGreaterThanEqual(price, pageable);
    }

    public Page<Product> findAllByPriceLessThan(BigDecimal price, Pageable pageable) {
        return productRepository.findAllByPriceLessThanEqual(price, pageable);
    }

    public Page<Product> findAllByPriceBetween(BigDecimal priceMax, BigDecimal priceMin, Pageable pageable) {
        return productRepository.findAllByPriceBetween(priceMax, priceMin, pageable);
    }

    public Page<Product> findByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategory(categoryId, pageable);
    }

    public void saveProduct (Product product) {

        product.setActive(true);
        product.setId(null);
        productRepository.save(product);
    }

    public void deleteProduct (Long id) {
        Product existingProduct = findById(id);
        existingProduct.setActive(false);
        productRepository.save(existingProduct);
    }

    public void updateProduct (Long id, Product product) {
        Product existingProduct = findById(id);

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setActive(product.getActive());
        existingProduct.setStock(product.getStock());
        existingProduct.setCategories(product.getCategories());
        productRepository.save(existingProduct);
    }


}
