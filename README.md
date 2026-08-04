# Vehiculos RD — Backend

API REST para la plataforma de compra y venta de vehículos (República Dominicana).

## Stack

- Java 17 + Spring Boot 3
- PostgreSQL
- Maven

## Cómo levantar el proyecto localmente

1. Instalar Java 17 y Maven.
2. Crear una base de datos PostgreSQL local llamada `vehiculos_rd`.
3. Copiar variables de entorno necesarias (ver `application.yml`):
   - `DB_USERNAME`, `DB_PASSWORD`
4. Ejecutar:
   ```bash
   mvn spring-boot:run
   ```
5. La API queda disponible en `http://localhost:8080`.

## Estructura

```
src/main/java/com/vehiculosrd/
├── auth/          # registro, login, JWT
├── vehiculos/     # publicaciones de vehículos
├── usuarios/      # perfiles de usuario
├── imagenes/      # integración con storage (R2/S3)
├── admin/         # panel de moderación
└── config/        # seguridad, CORS, beans generales
```

## Estado del proyecto

Scaffold inicial. Ver el documento de proyecto (`Proyecto_Plataforma_Vehiculos_RD.docx`) para el alcance completo del MVP y el roadmap.

## Endpoints planeados (MVP)

Ver tabla completa de endpoints en el documento de proyecto. Resumen:

- `POST /api/auth/registro`, `POST /api/auth/login`
- `GET /api/vehiculos`, `GET /api/vehiculos/{id}`, `POST /api/vehiculos`, `PUT /api/vehiculos/{id}`, `DELETE /api/vehiculos/{id}`
- `POST /api/vehiculos/{id}/imagenes`
- `GET /api/usuarios/{id}`
- `POST /api/vehiculos/{id}/contacto`
- `GET /api/admin/vehiculos`, `PATCH /api/admin/vehiculos/{id}/estado`
