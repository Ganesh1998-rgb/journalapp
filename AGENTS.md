# AGENTS.md: AI Coding Agent Guide for JournalApp

## Project Overview
- **Monorepo** for a journaling platform with microservice architecture.
- **Modules:**
  - `journal-service`: Core REST API for journal entries and user management.
  - `notification-service`: Listens to Kafka for notifications (e.g., email).
  - `common`: Shared DTOs and utility code.
- **Tech Stack:** Spring Boot 3, Java 17, MongoDB, Redis, Kafka, Docker, K8s.

## Architecture & Data Flow
- **journal-service** exposes REST endpoints (see `controllers/`).
- **Kafka** is used for async event communication:
  - `KafkaMessagePublisher` (journal-service) publishes to topic `demo`.
  - `KafkaConsumer` (notification-service) listens to `demo` (group: `notification-group`).
- **MongoDB** stores user and journal data. Redis is configured for caching.
- **DTOs** in `common/dto/` are used for API and inter-service communication.

## Build, Run, and Test
- **Build all modules:**
  - `./mvnw clean install` (from project root)
- **Run locally:**
  - Use `docker-compose.yaml` to start Kafka/Zookeeper/Kafdrop.
  - Each service can be run via its main class or `mvn spring-boot:run` in its directory.
- **Kubernetes:**
  - See `k8s/` for deployment/service manifests.
- **Config:**
  - Service configs in `src/main/resources/application.yml` (env vars for secrets/URIs).

## Project Conventions & Patterns
- **DTOs**: All API payloads use DTOs from `common` (e.g., `JournalEntryRequestDTO`).
- **Swagger/OpenAPI**: Enabled via `springdoc-openapi` (see `/doc` endpoint).
- **Kafka**: Topic names and group IDs are hardcoded (`demo`, `notification-group`).
- **User Context**: Authenticated user is resolved from `SecurityContextHolder` in service layer.
- **Error Handling**: API controllers return `ResponseEntity` with appropriate status codes.
- **Transactional**: MongoDB transactions enabled via `@EnableTransactionManagement` and `MongoTransactionManager` bean.

## Integration Points
- **Kafka**: All cross-service events use Kafka (see `KafkaMessagePublisher`, `KafkaConsumer`).
- **MongoDB/Redis**: URIs are injected via env vars (`MONGO_URI`, `REDIS_URL`).
- **Mail**: SMTP config in `application.yml` for notification-service.

## Key Files & Directories
- `journal-service/src/main/java/com/ganesh/journalapp/controllers/`: REST API endpoints
- `journal-service/src/main/java/com/ganesh/journalapp/kafka/`: Kafka integration
- `notification-service/src/main/java/com/ganesh/notification/kafka/`: Kafka consumer
- `common/src/main/java/com/ganesh/common/dto/`: Shared DTOs
- `docker-compose.yaml`, `k8s/`: Infra setup

## Example: Adding a New Event
1. Define a new DTO in `common/dto/`.
2. Publish event in `KafkaMessagePublisher` (journal-service).
3. Handle event in `KafkaConsumer` (notification-service).

---
For more, see `pom.xml` for dependencies and `application.yml` for config patterns.
