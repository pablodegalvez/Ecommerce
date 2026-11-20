package com.online.tienda.ecommerce.repository;

import com.online.tienda.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Trae todos los productos activos con sus categorías
    @Query(
            value = "SELECT p FROM Product p JOIN FETCH p.categories WHERE p.active = true",
            countQuery = "SELECT count(p) FROM Product p WHERE p.active = true"
    )
    Page<Product> findAllActiveProduct (Pageable pageable);

    // Filtro por precio máximo y mínimo
    @Query(
            value = "SELECT p FROM Product p JOIN FETCH p.categories WHERE p.price <= :priceMax AND p.price >= :priceMin AND p.active = true",
            countQuery = "SELECT count(p) FROM Product p WHERE p.price <= :priceMax AND p.price >= :priceMin AND p.active = true"
    )
    Page<Product> findAllByPriceBetween(@Param("priceMax") BigDecimal priceMax, @Param("priceMin") BigDecimal priceMin, Pageable pageable);

    // Filtro solo por precio máximo
    @Query(
            value = "SELECT p FROM Product p JOIN FETCH p.categories WHERE p.price <= :priceMax AND p.active = true",
            countQuery = "SELECT count(p) FROM Product p WHERE p.price <= :priceMax AND p.active = true"
    )
    Page<Product> findAllByPriceLessThanEqual(@Param("priceMax") BigDecimal priceMax, Pageable pageable);

    // Filtro solo por precio mínimo
    @Query(
            value = "SELECT p FROM Product p JOIN FETCH p.categories WHERE p.price > :priceMin AND p.active = true",
            countQuery = "SELECT count(p) FROM Product p WHERE p.price > :priceMin AND p.active = true"
    )
    Page<Product> findAllByPriceGreaterThanEqual(@Param("priceMin") BigDecimal priceMin, Pageable pageable);

    // Filtro por Categoría (Buscando por el ID de la categoría)
    @Query(
            value = "SELECT p FROM Product p JOIN FETCH p.categories c WHERE  c.id = :categoryId AND p.active = true",
            countQuery = "SELECT count(p) FROM Product p JOIN p.categories c WHERE c.id = :categoryId AND p.active = true"
    )
    Page<Product> findByCategory(@Param("categoryId")Long categoryId, Pageable pageable);

    // Filtro por nombre
    @Query(
            value = "SELECT p FROM Product p JOIN FETCH p.categories WHERE p.name = :productName AND p.active = true",
            countQuery = "SELECT count(p) FROM Product p JOIN p.categories WHERE p.name = :productName AND p.active = true"
    )
    Page<Product> findByNameAndActiveTrue(@Param("productName") String productName, Pageable pageable);


}
