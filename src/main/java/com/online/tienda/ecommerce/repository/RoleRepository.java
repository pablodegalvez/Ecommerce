package com.online.tienda.ecommerce.repository;

import com.online.tienda.ecommerce.entity.Role;
import com.online.tienda.ecommerce.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleType(RoleType roleType);
}
