# PolicySphere – Customer Service

Spring Boot 3 / Java 21 / MySQL. Runs on port 8081.

## Run
1. Start MySQL (credentials in `application.yml`; DB `customer_db` is auto-created).
2. `mvn spring-boot:run`

## Endpoints (`/api/v1/customers`)
| Method | Path | Purpose |
|---|---|---|
| POST | / | Create |
| GET | /{id} | Get one |
| GET | / | Get all |
| PUT | /{id} | Update |
| PATCH | /{id}/status | Deactivate |
