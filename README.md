# Tickets API

REST API for a support ticket system built with **Spring Boot**. Users open tickets, which move through a defined lifecycle with priorities and business rules enforced at the service layer.

## Tech Stack

- Java 17
- Spring Boot 3.2 (Web, Data JPA, Validation)
- MySQL (default) or H2 in-memory database
- Maven

## Features

- Create and deactivate users (soft delete: data is preserved)
- Open tickets with a priority: `BAJA`, `MEDIA` or `ALTA`
- Ticket lifecycle: `ABIERTO` → `EN_PROCESO` → `CERRADO`
- Reassign priority on tickets that are not closed
- List all tickets of a user
- Request validation with Bean Validation and consistent JSON error responses

## Business Rules

- Inactive users cannot open tickets
- A ticket description cannot be empty
- Closed tickets cannot change status or priority

## Architecture

```
src/main/java/com/example/tickets
├── controller   REST endpoints
├── service      Business logic and transactions
├── repository   Spring Data JPA repositories
├── model        JPA entities and enums
├── dto          Request/response records
└── exception    Custom exceptions and global error handler
```

## Getting Started

The project lives in the `tickets-api/` folder.

**With MySQL** (default): requires MySQL running on `localhost:3306` with user `root` / password `root`. The `tickets_db` database is created automatically.

```bash
cd tickets-api
mvn spring-boot:run
```

**With H2** (no database setup needed):

```bash
cd tickets-api
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

The API runs at `http://localhost:8080`.

## Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/usuarios` | Create a user |
| `PATCH` | `/api/usuarios/{id}/desactivar` | Deactivate a user |
| `GET` | `/api/usuarios/{id}/tickets` | List a user's tickets |
| `POST` | `/api/tickets` | Open a ticket |
| `PATCH` | `/api/tickets/{id}/estado` | Advance the ticket to its next status |
| `PATCH` | `/api/tickets/{id}/prioridad` | Reassign the ticket's priority |

### Examples

Create a user:

```json
POST /api/usuarios
{ "nombre": "Ana López" }
```

Open a ticket:

```json
POST /api/tickets
{
  "usuarioId": 1,
  "descripcion": "The printer on the second floor is not working",
  "prioridad": "ALTA"
}
```

Reassign priority:

```json
PATCH /api/tickets/1/prioridad
{ "prioridad": "MEDIA" }
```

## Error Handling

All errors return the same JSON structure:

```json
{
  "timestamp": "2026-10-21T10:15:30",
  "status": 409,
  "error": "Conflict",
  "mensajes": ["El ticket 1 está CERRADO y no se puede modificar"]
}
```

| Status | Cause |
|---|---|
| `400` | Invalid fields, malformed JSON or invalid enum value |
| `404` | User or ticket not found |
| `409` | Business rule violated |
| `500` | Unexpected server error |
