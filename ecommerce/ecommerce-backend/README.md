# Proyecto Universitario E-Commerce Backend (Java 21 & Spring Boot)
## Arquitectura Estándar, Limpia y Comprensible

Este proyecto implementa el backend de un sistema de comercio electrónico diseñado para una entrega universitaria de 3.er año, utilizando únicamente **Java nativo (Streams, synchronized, Math)** y **Spring Boot básico**.

---

## 🏛️ 1. Estructura del Proyecto

El proyecto está organizado de forma modular y clara:

```
src/main/java/com/ecommerce/backend/
├── config/                  # Configuración básica de beans de Spring (@Configuration)
├── controllers/             # Controladores REST (@RestController)
│   ├── AuthController.java
│   ├── AvatarController.java
│   ├── CartController.java
│   └── SearchController.java
├── dtos/                    # Clases DTO de transferencia de datos
│   ├── auth/                # LoginDTO, AuthResponse, LoginRequest
│   ├── avatar/              # AvatarUploadResponse
│   ├── cart/                # AddToCartRequest, CartResponse, CartItemResponse
│   └── search/              # SearchResponse
├── exceptions/              # Excepciones personalizadas y GlobalExceptionHandler
├── external/                # Mocks de almacenamiento para pruebas en memoria (S3)
├── models/                  # Clases de modelo (Product, User, Cart, CartItem, SearchCriteria)
├── repositories/            # Interfaces y repositorios en memoria (UserRepository, ProductRepository, CartRepository)
├── security/                # Codificación de contraseñas con BCrypt
└── services/                # Capa de Lógica de Negocio (4 Servicios Principales)
    ├── AuthService.java            # Autenticación, validación de credenciales y bloqueo de cuenta
    ├── SearchQueryBuilder.java     # Búsqueda, filtrado por precio y ordenamiento con Java Streams
    ├── CartService.java            # Carrito sincronizado (synchronized) y prevención de overflow con Math.addExact
    └── AvatarUploadService.java    # Subida de avatares con validación MIME de Spring y UUID
```

---

## 💻 2. Descripción de los 4 Servicios Principales

### 2.1. `AuthService.java` (Autenticación)
* **Validaciones básicas**: Comprueba que los campos email y password no estén vacíos antes de procesar la solicitud.
* **Normalización de Correo**: Aplica `.trim().toLowerCase()` para estandarizar el email ingresado.
* **Verificación de Credenciales**: Compara la contraseña usando `PasswordEncoder` (`BCrypt`).
* **Control de Intentos Fallidos**: Lleva el conteo de intentos erróneos (`failedLoginAttempts`). Al llegar al 5.º intento, la cuenta se bloquea automáticamente (`locked = true`). Si el login es exitoso, el contador se reinicia a 0.

### 2.2. `SearchQueryBuilder.java` (Búsqueda y Filtrado con Java Streams)
* **Filtrado por Texto**: Utiliza `products.stream().filter(...)` para buscar coincidencias (insensible a mayúsculas/minúsculas) en nombre, descripción, categoría o marca.
* **Filtrado por Rango de Precios**: Permite definir precios mínimos y máximos.
* **Ordenamiento Dinámico**: Aplica `.sorted(...)` con `Comparator` para ordenar por precio (ascendente/descendente) o por nombre alfabético.

### 2.3. `CartService.java` (Carrito de Compras y Control de Concurrencia)
* **Sincronización en Memoria**: El método `addItemToCart` utiliza la palabra reservada `synchronized` nativa de Java para asegurar que múltiples hilos o peticiones no generen condiciones de carrera sobre el carrito del usuario.
* **Prevención de Desbordamiento Aritmético**: Utiliza `Math.addExact(currentQty, requestedQty)` nativo de Java para detectar si la suma de cantidades excede el límite de enteros (`Integer.MAX_VALUE`).
* **Validación de Stock**: Verifica que la cantidad solicitada no supere las unidades disponibles del producto.

### 2.4. `AvatarUploadService.java` (Perfil y Carga de Imágenes)
* **Validación de Tamaño**: Limita el archivo a un máximo de 5 MB (`file.getSize() <= 5MB`).
* **Validación de Tipo MIME Nativo**: Lee `file.getContentType()` provisto por Spring Multipart para autorizar formatos de imagen (`image/jpeg`, `image/png`, `image/webp`).
* **Nombres Seguros con UUID**: Genera nombres aleatorios (`UUID.randomUUID()`) para evitar sobrescrituras de archivos y problemas de nombres conflictivos.

---

## 🚀 3. Ejecución y Pruebas

### Modo 1: Demostración Interactiva en Consola
* Clase: `com.ecommerce.backend.StandaloneSecurityDemo`
* Ejecuta un escenario completo por cada uno de los 4 servicios mostrando los resultados en consola.

### Modo 2: Pruebas Unitarias Automatizadas (JUnit 5)
* `AuthServiceTest.java`: Pruebas de login exitoso, normalización, usuario inexistente, bloqueo de cuenta y reset.
* `SearchQueryBuilderTest.java`: Pruebas de filtrado por texto, rangos de precio y ordenamiento con Streams.
* `CartServiceTest.java`: Pruebas de cálculo de subtotales, stock insuficiente, desbordamiento con Math.addExact y validaciones.
* `AvatarUploadServiceTest.java`: Pruebas de validación MIME de Spring, límite de tamaño (>5MB) y generación de UUID.

### Modo 3: Aplicación Spring Boot REST
* Clase: `com.ecommerce.backend.EcommerceApplication` (puerto `8080`).
