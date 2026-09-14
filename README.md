# Hotel Booking Platform — Backend API

A Spring Boot REST API for a hotel booking platform, built as **Task 05 (Mini Project)** for the Prodigy InfoTech Backend Development internship. Users can list their own hotel rooms, search and filter available rooms by date, and book rooms — all behind JWT-secured endpoints.

## Task Requirements

Per the assignment brief, the API needed to support:

- Endpoints for users to create, edit, and delete their own hotel room listings
- Endpoints to search and filter available hotel rooms by criteria such as check-in and check-out dates
- Room booking functionality, letting users reserve available rooms
- Secure access to user accounts via JWT authentication
- A relational database (MySQL) to store room details, user accounts, and bookings
- Input validation and proper error handling across all endpoints

## Tech Stack

- **Java 17**
- **Spring Boot 3.3.0**
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - Spring Validation
  - Spring Cache
- **MySQL** — relational datastore (`mysql-connector-j`)
- **Flyway** — versioned database migrations
- **Redis** — caching layer (TTL-based) to speed up room availability lookups
- **JJWT 0.12.6** — JSON Web Token generation & validation
- **spring-dotenv** — loads configuration from a `.env` file
- **Maven** — build tool

## Features

- User registration and authentication with stateless JWT access tokens
- Role/ownership-based access so users can only edit or delete their own room listings
- Room listing CRUD (create, edit, delete)
- Search and filter available rooms by check-in / check-out dates and other criteria
- Room booking — users can reserve available rooms
- Redis-backed caching with TTL to reduce repeated database load on availability queries
- Input validation and centralized error handling across endpoints
- Database schema versioning via Flyway migrations

## Prerequisites

- JDK 17+
- Maven 3.8+
- MySQL 8.x running locally or remotely
- Redis running locally or remotely

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/Yogesh-Bhatt-SWD/PRODIGY_BD_05.git
cd PRODIGY_BD_05
```

### 2. Configure environment variables

Copy the example environment file and fill in your own values:

```bash
cp .env.example .env
```

| Variable         | Description                                                      |
|------------------|--------------------------------------------------------------------|
| `DB_URL`         | JDBC connection string for your MySQL database (`hotel_booking_db` by default) |
| `DB_USERNAME`    | MySQL username                                                     |
| `DB_PASSWORD`    | MySQL password                                                     |
| `JWT_SECRET`     | Base64-encoded secret key (at least 256 bits) used to sign JWTs    |
| `REDIS_HOST`     | Redis host (e.g. `localhost`)                                      |
| `REDIS_PORT`     | Redis port (default `6379`)                                        |
| `REDIS_PASSWORD` | Redis password (leave blank if none)                                |

> **Never commit your `.env` file** — it's already excluded via `.gitignore`.

### 3. Create the database

Create an empty MySQL database matching the name in your `DB_URL` (default: `hotel_booking_db`). Flyway handles schema creation and migrations automatically on startup.

### 4. Build and run

```bash
mvn clean install
mvn spring-boot:run
```

The API starts on `http://localhost:8080` by default.

## Project Structure

```
PRODIGY_BD_05/
├── src/main/          # Application source (config, controllers, services, entities, security)
├── pom.xml            # Maven build configuration and dependencies
├── .env.example        # Template for required environment variables
└── .gitignore
```

## Core Domain

- **Users** — accounts that authenticate via JWT and own room listings/bookings
- **Rooms** — hotel room listings created, edited, and deleted by their owning user
- **Bookings** — reservations tying a user to a room for a given date range

## Authentication Flow

1. A user registers or logs in via the auth endpoints.
2. On successful login, the server issues a signed JWT.
3. The client includes this token in the `Authorization: Bearer <token>` header on subsequent requests.
4. Spring Security validates the token and enforces that room-listing edits/deletes are restricted to the owning user.

## Building for Production

```bash
mvn clean package
java -jar target/user-crud-api-0.0.1-SNAPSHOT.jar
```

## Contributing

This is a personal learning project built for the Prodigy InfoTech internship program. Issues and pull requests are welcome if you'd like to suggest improvements.

## License

No license specified yet — all rights reserved by default until one is added.
