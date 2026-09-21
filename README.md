
Smart Product Management System

A backend REST API built with **Java and Spring Boot** for managing products with **JWT authentication** and **role-based authorization**.

The application provides product CRUD operations, search, pagination, image upload, validation, centralized exception handling, and MySQL database integration.

---

## Features

- User registration and login
- JWT-based authentication
- Role-based authorization
- USER and ADMIN roles
- Product CRUD operations
- Product search by name, brand, and category
- Pagination
- Product image upload and retrieval
- Image storage in MySQL
- BCrypt password hashing
- Bean validation
- Centralized exception handling
- MySQL database integration

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming |
| Spring Boot | Application framework |
| Spring Web | REST APIs |
| Spring Security | Authentication & authorization |
| JWT | Token-based authentication |
| Spring Data JPA | Database access |
| Hibernate | ORM |
| MySQL | Database |
| Lombok | Boilerplate reduction |
| Maven | Build & dependency management |
| Postman | API testing |

---

## Architecture

src/main/java/com/devi/project/
│
├── controller/
│   ├── ProductController.java
│   ├── Registration.java
│   └── LoginController.java
│
├── model/
│   ├── Product.java
│   └── User.java
│
├── repository/
│   ├── ProductRepo.java
│   └── UserRepo.java
│
├── service/
│   └── ProductService.java
│
├── exception/
│   ├── ExceptionManager.java
│   └── UserAlreadyExit.java
│
├── security/
│   ├── SecurityConfig.java
│   ├── UserService.java
│   ├── UserRegister.java
│   ├── JwtService.java
│   ├── JwtFilter.java
│   └── DataSeeder.java
│
└── ProjectApplication.java

## API Endpoints

### Authentication

| Method | Endpoint       | Access |
| ------ | -------------- | ------ |
| POST   | `/register`    | Public |
| POST   | `/login`       | Public |
| GET    | `/api/v1/info` | Public |

### Products

| Method | Endpoint                                | Access       |
| ------ | --------------------------------------- | ------------ |
| GET    | `/api/v1/products?page=0&size=10`       | USER / ADMIN |
| GET    | `/api/v1/product/{id}`                  | USER / ADMIN |
| GET    | `/api/v1/product/{id}/image`            | USER / ADMIN |
| GET    | `/api/v1/products/search?keyword=phone` | USER / ADMIN |
| POST   | `/api/v1/product`                       | ADMIN        |
| PUT    | `/api/v1/product/{id}`                  | ADMIN        |
| PATCH  | `/api/v1/product/{id}`                  | ADMIN        |
| DELETE | `/api/v1/product/{id}`                  | ADMIN        |

---

## Authentication & Authorization

The application uses **JWT-based stateless authentication**.

### Authentication Flow

```text
User
 ↓
Login
 ↓
AuthenticationManager
 ↓
UserDetailsService
 ↓
BCrypt Password Verification
 ↓
JWT Generated
 ↓
JWT Returned
```

For protected requests:

```text
Client
 ↓
Authorization: Bearer <JWT>
 ↓
JwtFilter
 ↓
JWT Validation
 ↓
Role Authorization
 ↓
Controller
```

### Role Permissions

| Operation          | USER | ADMIN |
| ------------------ | :--: | :---: |
| View products      |   ✅  |   ✅   |
| Search products    |   ✅  |   ✅   |
| View product image |   ✅  |   ✅   |
| Add product        |   ❌  |   ✅   |
| Update product     |   ❌  |   ✅   |
| Delete product     |   ❌  |   ✅   |

Use the JWT token in the request header:

```text
Authorization: Bearer <your-jwt-token>
```

---

## Database Setup

The application uses **MySQL**.

### 1. Create Database

```sql
CREATE DATABASE productdb;
```

### 2. Configure Database

In `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/productdb
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
```

The `${DB_PASSWORD}` value is read from an environment variable.

### 3. Set Database Password

**Windows PowerShell:**

```powershell
$env:DB_PASSWORD="your_mysql_password"
```

**Linux / macOS:**

```bash
export DB_PASSWORD=your_mysql_password
```

> Never commit your actual database password or other sensitive information to GitHub.

---

## Run Locally

### Prerequisites

* Java 17+
* MySQL
* Maven
* Git

### Clone Repository

```bash
git clone https://github.com/Devi12655/smart_product_management_system.git
```

```bash
cd smart_product_management_system
```

### Start Application

**Windows:**

```powershell
mvnw.cmd spring-boot:run
```

**Linux / macOS:**

```bash
./mvnw spring-boot:run
```

You can also run `ProjectApplication.java` directly from your IDE.

---

## Testing

The APIs can be tested using **Postman**.

Typical testing flow:

```text
Register
   ↓
Login
   ↓
Copy JWT Token
   ↓
Add Bearer Token
   ↓
Test Protected APIs
```

---

## Validation & Exception Handling

The project uses **Bean Validation** and centralized exception handling.

Examples of validation:

* Product price must be positive
* Product quantity must satisfy the required minimum

Exception handling is implemented using:

```java
@ControllerAdvice
```

Common HTTP responses include:

```text
200 OK
201 CREATED
400 BAD REQUEST
401 UNAUTHORIZED
403 FORBIDDEN
404 NOT FOUND
500 INTERNAL SERVER ERROR
```

---

## Security

Sensitive information should not be committed to GitHub.

Do not commit:

```text
.env
Database passwords
JWT secrets
Private credentials
target/
```

Sensitive configuration should be provided through environment variables.

---

## Future Enhancements

* DTO implementation
* Swagger / OpenAPI documentation
* JWT expiration and refresh tokens
* Unit and integration testing
* Advanced filtering and sorting
* Flyway / Liquibase database migrations
* Docker support
* CI/CD pipeline
* Secure external image storage

---

