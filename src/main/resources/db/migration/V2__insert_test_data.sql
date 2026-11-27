-- src/main/resources/db/migration/V2__insert_test_data.sql

-- 1. Insertar Roles
INSERT INTO role (roletype) VALUES ('ROLE_USER');
INSERT INTO role (roletype) VALUES ('ROLE_ADMIN');

-- 2. Insertar Categorías
INSERT INTO category (name) VALUES ('Electronics');
INSERT INTO category (name) VALUES ('Books');

-- 3. Insertar Productos
INSERT INTO product (name, description, price, stock, active)
VALUES ('Smartphone', 'Latest generation mobile phone', 699.99, 50, TRUE);

INSERT INTO product (name, description, price, stock, active)
VALUES ('Java Programming Book', 'Learn Spring Boot from scratch', 45.50, 120, TRUE);

INSERT INTO product_category (product_id, category_id) VALUES (1, 1);
INSERT INTO product_category (product_id, category_id) VALUES (2, 2);

-- 4. NUEVO: Insertar Usuarios de Prueba (Contraseña en texto plano: "password123")
-- Nota: Insertamos la cadena encriptada con BCrypt directamente en el SQL
INSERT INTO users (username, email, password, enabled)
VALUES ('user_normal', 'user@example.com', '$2a$10$X5o1wAEvYwK6kQG21tqZPeK9H3V5C2E7K6vM9v2v3v4v5v6v7v8v9', TRUE);

INSERT INTO users (username, email, password, enabled)
VALUES ('user_admin', 'admin@example.com', '$2a$10$X5o1wAEvYwK6kQG21tqZPeK9H3V5C2E7K6vM9v2v3v4v5v6v7v8v9', TRUE);

-- 5. NUEVO: Asignar Roles a los Usuarios
-- Usuario Normal (ID 1) -> ROLE_USER (ID 1)
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);

-- Usuario Admin (ID 2) -> ROLE_USER (ID 1) y ROLE_ADMIN (ID 2)
INSERT INTO user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);