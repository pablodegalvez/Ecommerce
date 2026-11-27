-- src/main/resources/db/migration/V1__init_schema.sql

-- 1. Tablas Maestras (No dependen de ninguna otra)
CREATE TABLE role (
                      id BIGSERIAL PRIMARY KEY,
                      roletype VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE category (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL UNIQUE
);

-- Usamos 'users' para evitar conflictos con la palabra reservada 'user' en PostgreSQL
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(30) NOT NULL,
                       password VARCHAR(255) NOT NULL, -- Mayor tamaño para almacenar el hash de BCrypt de 60 caracteres
                       email VARCHAR(255) NOT NULL UNIQUE,
                       enabled BOOLEAN DEFAULT TRUE
);

CREATE TABLE product (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL UNIQUE,
                         description TEXT,
                         price NUMERIC(10, 2) NOT NULL, -- Equivale a tu BigDecimal con precisión 10,2
                         stock INTEGER NOT NULL,
                         active BOOLEAN DEFAULT TRUE
);

-- 2. Tablas Intermedias (Relaciones Muchos a Muchos)

-- Relación: Users <-> Role
CREATE TABLE user_role (
                           user_id BIGINT NOT NULL,
                           role_id BIGINT NOT NULL,
                           PRIMARY KEY (user_id, role_id),
                           CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                           CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

-- Relación: Users <-> Product (Wishlist)
CREATE TABLE user_wishlist (
                               user_id BIGINT NOT NULL,
                               product_id BIGINT NOT NULL,
                               PRIMARY KEY (user_id, product_id),
                               CONSTRAINT fk_wishlist_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                               CONSTRAINT fk_wishlist_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

-- Relación: Product <-> Category
CREATE TABLE product_category (
                                  product_id BIGINT NOT NULL,
                                  category_id BIGINT NOT NULL,
                                  PRIMARY KEY (product_id, category_id),
                                  CONSTRAINT fk_prod_cat_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
                                  CONSTRAINT fk_prod_cat_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);