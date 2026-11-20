package com.online.tienda.ecommerce.repository;

import com.online.tienda.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
