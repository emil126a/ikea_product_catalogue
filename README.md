# 🛍️ IKEA Product Management System

A full-stack application for managing products, built with a **React + Vite** frontend and a **Java 21 + Spring Boot** backend, adhering to **Domain-Driven Design (DDD)** principles.

---

## 🧰 Tech Stack

- **Frontend**: React, Vite, Tailwind CSS
- **Backend**: Java 21, Spring Boot, JPA, Lombok
- **API Documentation**: Swagger UI
- **Containerization**: Docker, Docker Compose

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/emil126a/ikea_product_catalogue.git
cd product-catalogue
```

### 2. Run with Docker Compose

To build and start the application:

```bash
docker compose up --build
```

Or, if already built:

```bash
docker compose up
```

### 3. 🌐 Access the Application
- **Enter**: [http://localhost/products](http://localhost/products)
- **Swagger API Docs**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 📦 API Endpoints (Examples)

All APIs are accessible via Swagger. Below are some example endpoints:

| Endpoint            | Method | Description                 |
|---------------------|--------|-----------------------------|
| `/products`         | GET    | List all products           |
| `/products/{id}`    | GET    | Get product details         |
| `/products`         | POST   | Create a new product        |
| `/product-types`    | GET    | Get all product types       |
| `/colours`          | GET    | Get all available colours   |

---

## 📂 Project Structure

### Backend

```
src/main/java/ikea/product/demo/
├── config             # CORS and Security configurations
├── controller/api     # API endpoints
├── dto                # Request and Response DTOs
├── entity             # JPA Entities
├── exception          # Custom exception handling
├── mapper             # Entity-DTO mappers
├── repository         # Spring Data JPA Repositories
├── service            # Business logic
└── validation         # Custom validators
```

### Frontend

```
frontend/
├── src/
│   ├── components/    # Reusable UI components
│   ├── pages/         # Page components
│   ├── services/      # API service calls
│   └── main.tsx       # Entry point
├── public/            # Static assets
├── index.html         # HTML template
└── tailwind.config.js # Tailwind CSS configuration
```

---

## 🧪 Running Tests (Backend)

```bash
./mvnw test
```

---

## 📌 Notes

- Ensure ports **80** and **8080** are free before starting.
- The database is managed via **Docker Compose** — no manual setup required.
- Environment variables are configured via `.env` files and `application.properties`.