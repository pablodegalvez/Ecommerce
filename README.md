#  E-Commerce Backend Service

Tienda online. Este proyecto es una API REST desarrollada en **Java** utilizando el ecosistema de **Spring Boot**. Está diseñado bajo una arquitectura por capas (Controlador -> Servicio -> Repositorio).

El objetivo principal de este proyecto es consolidar mis habilidades técnicas y ganar la solidez necesaria para afrontar retos de nivel profesional en el desarrollo Backend.

---

##  Características Clave e Hitos del Proyecto

El sistema se está construyendo de forma incremental dividiéndose en los siguientes bloques funcionales:

*   **Catálogo Paginado y Filtros Dinámicos:** Gestión completa de productos y categorías (`@ManyToMany`). Soporta búsquedas eficientes con paginación nativa de Spring Data y filtros combinados por precio (`BigDecimal`) y disponibilidad (borrado lógico).
*   **Seguridad y Control de Accesos:** Autenticación y autorización basada en **Spring Security** y tokens **JWT (Stateless)**, utilizando el correo electrónico como identificador único y roles diferenciados (`ROLE_USER` y `ROLE_ADMIN`).
*   **Persistencia Híbrida (PostgreSQL + Redis):** Lista de deseos guardada en base de datos relacional y gestión del carrito de compras utilizando almacenamiento en caché en memoria con **Redis**.
*   **Pasarelas de Pago:** Procesamiento de compras a través de **Stripe** y **PayPal** aplicando el **Patrón de Diseño Strategy** para desacoplar las implementaciones.
*   **Comunicación en Tiempo Real:** Chat de soporte directo entre el Cliente y el Administrador utilizando **WebSockets con protocolo STOMP**.

---

##  Stack Tecnológico

*   **Lenguaje:** Java 25
*   **Framework Principal:** Spring Boot 4.x
*   **Persistencia:** Spring Data JPA / Hibernate
*   **Bases de Datos:** PostgreSQL (Relacional) & Redis (En memoria / Caché)
*   **Seguridad:** Spring Security, JWT (JSON Web Tokens), BCrypt
*   **Herramientas de Infraestructura:** Docker & Docker Compose
*   **Documentación:** Swagger UI / OpenAPI
*   **Gestor de Dependencias:** Maven

---

##  Infraestructura de Desarrollo (Local)(**¡¡Todavía no implementado!!**)

Para levantar el entorno local con las bases de datos necesarias, el proyecto cuenta con soporte para Docker. Solo es necesario ejecutar el siguiente comando en la raíz del proyecto:

```bash
docker-compose up -d
```

