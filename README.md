# Smart Product Management System

A full-stack Product Management System built using **Java, Spring Boot, Spring Security, JWT, Spring Data JPA, MySQL, HTML, CSS, and JavaScript**.

The application provides secure user authentication, role-based authorization, product management, product search, pagination, and product image upload and retrieval.

## Features

* User registration and login
* JWT-based authentication
* Role-based authorization using `USER` and `ADMIN`
* Product CRUD operations
* Product search by name, brand, and category
* Pagination
* Product image upload and retrieval
* Image storage in MySQL as BLOB
* BCrypt password hashing
* Input validation
* Centralized exception handling
* MySQL database integration

## Tech Stack

| Technology            | Purpose                          |
| --------------------- | -------------------------------- |
| Java                  | Backend programming              |
| Spring Boot           | Application framework            |
| Spring Web            | REST APIs                        |
| Spring Security       | Authentication and authorization |
| JWT                   | Token-based authentication       |
| Spring Data JPA       | Database access                  |
| Hibernate             | ORM                              |
| MySQL                 | Database                         |
| Lombok                | Boilerplate reduction            |
| HTML, CSS, JavaScript | Frontend                         |
| Maven                 | Build and dependency management  |
| Postman               | API testing                      |
## System Architecture
<img width="2415" height="2929" alt="mermaid-diagram" src="https://github.com/user-attachments/assets/9eab601a-8ec5-4430-a5d1-0613e81dfa4d" />

The application follows a **layered architecture** using Controller, Service, and Repository layers. Spring Security and JWT handle authentication and role-based authorization.

## API Endpoints

### Base URL

```text
/api/v1
```

### Authentication APIs

| Method | Endpoint       | Access | Description             |
| ------ | -------------- | ------ | ----------------------- |
| GET    | `/api/v1/info` | Public | Application information |
| POST   | `/register`    | Public | Register a new user     |
| POST   | `/login`       | Public | Login and receive JWT   |

### Product APIs

| Method | Endpoint                                               | Access      | Description                  |
| ------ | ------------------------------------------------------ | ----------- | ---------------------------- |
| GET    | `/api/v1/products?page=0&size=10`                      | USER, ADMIN | Get products with pagination |
| GET    | `/api/v1/product/{id}`                                 | USER, ADMIN | Get product by ID            |
| GET    | `/api/v1/products/search?keyword=phone&page=0&size=10` | USER, ADMIN | Search products              |
| GET    | `/api/v1/product/{id}/image`                           | USER, ADMIN | Get product image            |
| POST   | `/api/v1/product`                                      | ADMIN       | Add product                  |
| PUT    | `/api/v1/product/{id}`                                 | ADMIN       | Update product               |
| PATCH  | `/api/v1/product/{id}`                                 | ADMIN       | Partially update product     |
| DELETE | `/api/v1/product/{id}`                                 | ADMIN       | Delete product               |

### Product Search

Products can be searched using:

* Name
* Brand
* Category

Search supports case-insensitive partial matching.

Example:

```text
GET /api/v1/products/search?keyword=phone&page=0&size=10
```

### Image Upload

Product creation and update support:

```text
multipart/form-data
```

Product images are stored in MySQL as binary data and can be retrieved through the image endpoint.

## Authentication and Authorization

The application uses **stateless JWT authentication**.

### Authentication Flow

```text
User
  ↓
Register / Login
  ↓
AuthenticationManager
  ↓
UserService
  ↓
User Repository
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

### Roles and Permissions

| Operation          | USER | ADMIN |
| ------------------ | :--: | :---: |
| View products      |   ✅  |   ✅   |
| Search products    |   ✅  |   ✅   |
| View product image |   ✅  |   ✅   |
| Add product        |   ❌  |   ✅   |
| Update product     |   ❌  |   ✅   |
| Partial update     |   ❌  |   ✅   |
| Delete product     |   ❌  |   ✅   |

Passwords are stored using **BCrypt hashing** rather than plain text.

## Database Setup

The application uses **MySQL**.

### Create Database

```sql
CREATE DATABASE productdb;
```

### Database Configuration

In `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/productdb
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
```

Set the MySQL password using an environment variable.

**Windows PowerShell:**

```powershell
$env:DB_PASSWORD="your_mysql_password"
```

**Linux/macOS:**

```bash
export DB_PASSWORD=your_mysql_password
```

## Run Locally

### Prerequisites

* Java 17+
* Maven
* MySQL
* Git

### Clone the Repository

```bash
git clone https://github.com/Devi12655/smart_product_management_system.git
```

```bash
cd smart_product_management_system
```

### Start the Application

**Windows:**

```bash
mvnw.cmd spring-boot:run
```

**Linux/macOS:**

```bash
./mvnw spring-boot:run
```

Or run `ProjectApplication.java` from your IDE.

## Testing with Postman

Recommended testing flow:

```text
Register
   ↓
Login
   ↓
Copy JWT Token
   ↓
Authorization → Bearer Token
   ↓
Call Protected Product APIs
```

Authorization header:

```text
Authorization: Bearer <your-jwt-token>
```

## Validation and Exception Handling

The project uses Bean Validation for product inputs.

Examples:

```text
price > 0
quantity >= 1
```

Validation and application-specific exceptions are handled centrally using:

```java
@ControllerAdvice
```

## Security

Sensitive information should not be committed to GitHub.

Do not commit:

```text
.env
database passwords
JWT secrets
private credentials
target/
.vscode/
```

Sensitive configuration should be provided through environment variables.

