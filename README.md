
# Layered Architecture Demo

This repository implements a layered version of the [Buckpal](https://github.com/thombergs/buckpal) demo application, which was originally designed to demonstrate the principles of hexagonal architecture. The project provides a lightweight web application organized into presentation, business and data layers.

**Disclaimer:** This project intentionally violates standard architectural best practices (e.g., no dependency inversion, tight coupling between layers) to demonstrate issues in strict layered architectures and contrast them with hexagonal architecture principles. It is for educational purposes only and should not be used as production code.

  

## Technology Stack

  

- Java 17

- Spring Boot 3.1

- Gradle

- Spring Data JPA / Hibernate

- H2 (in-memory database for tests and local runs)

- JUnit 5, Mockito, AssertJ, ArchUnit

  

## Architecture Overview

  

The project follows a classic 3-layer structure including **presentation**, **business**, and **data** layers.



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

The application starts on `http://localhost:8080`.

  

The H2 console is available at `http://localhost:8080/h2-console`. Set `JDBC_URL = jdbc:h2:mem:db` and `Driver class = org.h2.Driver`

  

4. Generate Javadoc:

```bash

./gradlew javadoc

```

Documentation is generated in the project root directory.

  

## API Endpoints

  

-  `GET /accounts/balance?accountId={id}`

-  `POST /accounts/send`

-  `POST /accounts/deposit`

-  `POST /accounts/withdraw`

-  `POST /accounts/create`

-  `GET /accounts`