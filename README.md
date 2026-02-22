# java-spring-kafka-react

Full-stack, modern demo app:

- **Backend:** Java 17 + Spring Boot (Web, Actuator, JPA)
- **Database:** Postgres (Docker)
- **Messaging:** Kafka via Redpanda (Docker)
- **Frontend:** React + TypeScript (Vite)
- **Server-state:** TanStack Query (React Query)
- **Tooling:** ESLint (Airbnb) + Prettier

## Architecture

### Backend
- REST API for creating and listing messages
- Messages are persisted to Postgres
- On create, the service publishes an event to Kafka
- A consumer listens to the topic and logs `Kafka received ...`
- **Idempotency-Key** header prevents duplicate creates on retries

### Frontend
- Simple UI for sending and listing messages
- Uses TanStack Query for caching + invalidation
- Uses an API layer (`src/api/*`) to keep transport logic out of components

## Run with Docker (recommended)

This spins up **Postgres + Redpanda + backend + frontend**:

```bash
docker compose up --build
