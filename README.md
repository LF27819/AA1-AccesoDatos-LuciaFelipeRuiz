# Eventia API

API REST para la gestión de eventos musicales, desarrollada con **Spring Boot 3** y **MariaDB**. 
Permite administrar artistas, recintos, eventos, reservas y usuarios.

---

## Tecnologías

- **Java 21**
- **Spring Boot 3.5.6**
  - Spring Data JPA
  - Spring Web
  - Spring Validation
- **MariaDB 11.3.2** (vía Docker)
- **Lombok**
- **WireMock 4.0.0-beta.29** (API mock para pruebas)
- **Maven** 

---

## Estructura del proyecto

```
AA1-AccesoDatos-LuciaFelipeRuiz/
├── eventia/eventia/                  # Proyecto Spring Boot principal
│   ├── src/
│   │   ├── main/java/com/svalero/eventia/
│   │   │   ├── controller/           # Controladores REST
│   │   │   ├── domain/               # Entidades JPA
│   │   │   ├── exception/            # Excepciones personalizadas
│   │   │   ├── repository/           # Repositorios Spring Data
│   │   │   └── service/              # Lógica de negocio
│   │   └── main/resources/
│   │       ├── application.properties
│   │       └── logback-spring.xml
│   ├── src/test/                     # Tests unitarios e integración
│   ├── Postman/eventia-postman.json  # Colección Postman
│   ├── openapi-eventia.yaml          # Especificación OpenAPI
│   ├── docker-compose.dev.yaml       # Base de datos en Docker
│   └── pom.xml
└── apimock/                          # Mock WireMock
    ├── mappings/                     # Definición de endpoints mock
    ├── __files/                      # Respuestas JSON del mock
    └── wiremock-standalone-4.0.0-beta.29.jar
```

---

## Modelo de datos

### Entidades y relaciones

```
Usuario ──< Evento >── Artista
               │
            Recinto

Usuario ──< Reserva >── Evento
```

| Entidad   | Campos principales |
|-----------|-------------------|
| `Artista` | id, nombreArtistico, nombreReal, generoMusical, fechaNacimiento, activo, cache, eventosRealizados |
| `Recinto` | id, nombre, direccion, ciudad, capacidad, cubierto, precioAlquiler, eventosCelebrados, fechaInauguracion |
| `Evento`  | id, nombre, descripcion, fechaEvento, horaEvento, precioEntrada, aforoMaximo, entradasDisponibles, cancelado, presencial, categoria · FK: usuario, artista, recinto |
| `Reserva` | id, fechaReserva, cantidadEntradas, precioTotal, metodoPago, codigoReserva (único), confirmada · FK: usuario, evento |
| `Usuario` | id, nombre, apellidos, email (único), password, telefono, activo, fechaNacimiento, eventosAsistidos, rol, saldoCuenta |

---

## Endpoints de la API

La API escucha por defecto en `http://localhost:8080`.

### Artistas `/artistas`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/artistas` | Listar todos (filtros: `nombreArtistico`, `generoMusical`, `activo`) |
| GET | `/artistas/{id}` | Obtener por ID |
| GET | `/artistas/activos` | Listar solo artistas activos |
| POST | `/artistas` | Crear artista |
| PUT | `/artistas/{id}` | Actualizar artista completo |
| PATCH | `/artistas/{id}` | Actualizar parcialmente |
| DELETE | `/artistas/{id}` | Eliminar artista |

### Recintos `/recintos`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/recintos` | Listar todos |
| GET | `/recintos/{id}` | Obtener por ID |
| POST | `/recintos` | Crear recinto |
| PUT | `/recintos/{id}` | Actualizar recinto completo |
| PATCH | `/recintos/{id}` | Actualizar parcialmente |
| DELETE | `/recintos/{id}` | Eliminar recinto |

### Eventos `/eventos`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/eventos` | Listar todos |
| GET | `/eventos/{id}` | Obtener por ID |
| GET | `/eventos/cancelados` | Listar eventos cancelados |
| POST | `/eventos` | Crear evento |
| PUT | `/eventos/{id}` | Actualizar evento completo |
| PATCH | `/eventos/{id}` | Actualizar parcialmente |
| DELETE | `/eventos/{id}` | Eliminar evento |

### Reservas `/reservas`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/reservas` | Listar todas |
| GET | `/reservas/{id}` | Obtener por ID |
| GET | `/reservas/confirmadas` | Listar reservas confirmadas |
| POST | `/reservas` | Crear reserva |
| PUT | `/reservas/{id}` | Actualizar reserva completa |
| PATCH | `/reservas/{id}` | Actualizar parcialmente |
| DELETE | `/reservas/{id}` | Eliminar reserva |

### Usuarios `/usuarios`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/usuarios` | Listar todos |
| GET | `/usuarios/{id}` | Obtener por ID |
| POST | `/usuarios` | Crear usuario |
| PUT | `/usuarios/{id}` | Actualizar usuario completo |
| PATCH | `/usuarios/{id}` | Actualizar parcialmente |
| DELETE | `/usuarios/{id}` | Eliminar usuario |

### Códigos de respuesta

| Código | Significado |
|--------|-------------|
| `200 OK` | Operación correcta |
| `201 Created` | Recurso creado |
| `400 Bad Request` | Datos de entrada inválidos |
| `404 Not Found` | Recurso no encontrado |
| `500 Internal Server Error` | Error del servidor |

---

## Puesta en marcha

### Requisitos previos

- **Java 21** o superior
- **Docker** y **Docker Compose** (para la base de datos)
- **Maven** (o usar el wrapper `./mvnw` incluido)

---

### 1. Levantar la base de datos con Docker

Desde el directorio `eventia/eventia/`:

```bash
docker compose -f docker-compose.dev.yaml up -d
```

Esto arranca un contenedor **MariaDB 11.3.2** con:

| Parámetro | Valor |
|-----------|-------|
| Host | `localhost` |
| Puerto | `3309` |
| Base de datos | `eventia1_db` |
| Usuario | `user` |
| Contraseña | `pass` |

---

### 2. Ejecutar la aplicación Spring Boot

Desde el directorio `eventia/eventia/`:

```bash
mvn spring-boot:run
```

La API estará disponible en: **`http://localhost:8080`**

> Spring Boot creará automáticamente las tablas en la base de datos al arrancar (`spring.jpa.hibernate.ddl-auto=update`).

---

### 3. (Opcional) Ejecutar el API Mock con WireMock

El mock simula los endpoints de la API con respuestas predefinidas para pruebas sin necesidad de base de datos. Desde el directorio `apimock/`:

```bash
java -jar wiremock-standalone-4.0.0-beta.29.jar --port 8081
```

### 4. Ejecutar los tests

```bash
mvn test
```

Los tests incluyen:

- **Tests de servicio** (`*ServiceTests`)
- **Tests de controlador** (`*ControllerTests`)

Todas las entidades cubiertas: `Artista`, `Evento`, `Recinto`, `Reserva` y `Usuario`.

---

## Importar la colección Postman

El archivo `Postman/eventia-postman.json` contiene una colección lista para importar en **Postman** con todos los endpoints configurados.

1. Abrir Postman → **Import**
2. Seleccionar el archivo `eventia-postman.json`
3. Apuntar la variable base URL a `http://localhost:8080`

---

## Configuración

El archivo de configuración principal es `src/main/resources/application.properties`:

```properties
spring.application.name=eventia
server.port=8080

spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mariadb://localhost:3309/eventia1_db
spring.datasource.username=user
spring.datasource.password=pass
spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect
```

Los logs se generan en el directorio `logs/` con rotación diaria, configurado en `logback-spring.xml`.

---

## Especificación OpenAPI

La especificación completa de la API está disponible en `openapi-eventia.yaml`. Se puede visualizar en Swagger Editor.
