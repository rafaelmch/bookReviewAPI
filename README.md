# Book Review API

A Spring Boot–based RESTful API for managing authors, books, and reviews. Built with Java 17, Spring Data JPA, PostgreSQL, and Flyway migrations, and documented with OpenAPI (Swagger UI).

---
## 📖 Overview

This API allows you to:

- **Create**, **list**, **retrieve**, **update**, and **delete** authors
- **Create**, **list**, **retrieve**, **update**, and **delete** books (each linked to an author)
- **Create**, **list**, **retrieve**, **update**, and **delete** reviews (each linked to a book)
- **Page** through large result sets

It demonstrates:

- **JPA entity mappings** (`@OneToMany`, `@ManyToOne`)
- **Pagination** with Spring Data (`Page<T>`, `Pageable`)
- **Flyway** database migrations
- **Bean Validation** (`@Valid`, `@NotBlank`, `@Min`, `@Max`)
- **OpenAPI / Swagger UI** for interactive API docs
- **Exception handling** via `@ControllerAdvice`

---
## 🛠️ Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Flyway
- Lombok
- Springdoc OpenAPI (Swagger UI)

---
## 🚀 Getting Started

### Prerequisites

- JDK 17+
- Maven 3.6+
- PostgreSQL 12+

### Configuration

1. Copy **`src/main/resources/application.properties`** and configure your database credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/bookreview_db
   spring.datasource.username=postgres
   spring.datasource.password=your_password
   ```
2. If you need a different port or server settings, adjust in the same file.

### Database Setup

- **Locally**:
  ```bash
  createdb bookreview_db
  mvn flyway:migrate
  ```

### Build & Run

```bash
cd bookReview
mvn clean package
java -jar target/book-review-api-0.0.1-SNAPSHOT.jar
```
or run the `BookReviewApplication` main class in your IDE.

By default, the API listens on **`http://localhost:8081`**.

---
## 📚 API Endpoints

### Authors

| Method | Endpoint           | Description                 |
|--------|--------------------|-----------------------------|
| GET    | `/authors`         | List authors (paged)        |
| GET    | `/authors/{id}`    | Retrieve a single author    |
| POST   | `/authors`         | Create an author            |
| PUT    | `/authors/{id}`    | Update an author            |
| DELETE | `/authors/{id}`    | Delete an author            |

Example: Create an author
```bash
curl -X POST http://localhost:8081/authors \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Isaac Asimov",
    "biography": "Russian-born American science fiction writer and biochemist."
  }'
```

### Books

| Method | Endpoint           | Description                                  |
|--------|--------------------|----------------------------------------------|
| GET    | `/books`           | List books (paged, filter by `?authorId=`)   |
| GET    | `/books/{id}`      | Retrieve a single book                       |
| POST   | `/books`           | Create a book                                |
| PUT    | `/books/{id}`      | Update a book                                |
| DELETE | `/books/{id}`      | Delete a book                                |

Example: Create a book with reviews
```bash
curl -X POST http://localhost:8081/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Foundation",
    "description": "A saga of galactic empire fall.",
    "publishedDate": "1951-06-19",
    "author": { "id": 3 },
    "reviews": [
      { "rating": 5, "comment": "A brilliant start." },
      { "rating": 4, "comment": "Complex and visionary." }
    ]
  }'
```

### Reviews

| Method | Endpoint           | Description                 |
|--------|--------------------|-----------------------------|
| GET    | `/reviews`         | List reviews (paged)        |
| GET    | `/reviews/{id}`    | Retrieve a single review    |
| POST   | `/reviews`         | Create a review             |
| PUT    | `/reviews/{id}`    | Update a review             |
| DELETE | `/reviews/{id}`    | Delete a review             |

Example: Create a review
```bash
curl -X POST http://localhost:8081/reviews \
  -H "Content-Type: application/json" \
  -d '{
    "rating": 5,
    "comment": "Absolutely loved it!",
    "book": { "id": 10 }
  }'
```

---
## 📄 License

This project is licensed under the MIT License.

