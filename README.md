# BuckPal N-Tier Demo

This repository implements a small web app using a 3-tier architecture (controller, service, repository) with a focused domain model and DTO layer.

## Technology Stack

- Java 17
- Spring Boot 3.1
- Gradle
- Spring Data JPA / Hibernate
- H2 (in-memory database for tests and local runs)
- JUnit 5, Mockito, AssertJ, ArchUnit

## Architecture Overview

The project follows a classic 3-tier structure:

- **Controller**: REST endpoints and request/response DTOs
- **Service**: Business logic orchestration
- **Repository**: Persistence and data mapping
- **Entity/DTO**: Domain objects and API contracts

Key packages:

- `io.reflectoring.buckpal.controller`
- `io.reflectoring.buckpal.service`
- `io.reflectoring.buckpal.repository`
- `io.reflectoring.buckpal.entity`
- `io.reflectoring.buckpal.dto`

## Prerequisites

- JDK 17
- Lombok annotation processing enabled in your IDE

## Build and Run

1. Build:
	```bash
	./gradlew build
	```

2. Run tests:
	```bash
	./gradlew test
	```

3. Run the application:
	```bash
	./gradlew bootRun
	```
	The service starts on `http://localhost:8080`.

4. Generate Javadoc:
	```bash
	./gradlew javadoc
	```
	Documentation is generated in the project root directory.

## API Endpoints

- `GET /accounts/balance?accountId={id}`
- `POST /accounts/send`
- `POST /accounts/deposit`
- `POST /accounts/withdraw`
- `POST /accounts/create`
- `GET /accounts`
