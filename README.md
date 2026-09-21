Smart Product Management System

A backend REST API built with Java, Spring Boot, Spring Data JPA, MySQL, Spring Security, and JWT for managing products with role-based access control.

The project demonstrates a layered Spring Boot architecture with product CRUD operations, pagination, search, image upload, validation, centralized exception handling, user registration, JWT authentication, and USER/ADMIN authorization.

Features

Product CRUD operations

Product pagination

Product search by name, brand, and category

Product image upload and retrieval

Image storage using database BLOB

User registration

BCrypt password encryption

JWT-based authentication

Role-based authorization

USER and ADMIN roles

Centralized exception handling

Bean validation

Stateless Spring Security configuration

Automatic admin user seeding

MySQL database integration

Tech Stack

Technology

Purpose

Java

Backend programming

Spring Boot

Application framework

Spring Web

REST APIs

Spring Data JPA

Database access

Hibernate

ORM

MySQL

Relational database

Spring Security

Authentication & authorization

JWT

Token-based authentication

BCrypt

Password hashing

Lombok

Boilerplate reduction

Bean Validation

Request validation

Maven

Dependency management

Project Architecture

Client / Postman
       |
       v
Spring Security
       |
       v
JWT Filter
       |
       v
Controller
       |
       v
Service
       |
       v
Repository
       |
       v
MySQL

The project follows a layered architecture:

Controller
    ↓
Service
    ↓
Repository
    ↓
Database

Security is handled separately through Spring Security and JWT.

Project Structure

src/
└── main/
    ├── java/
    │   └── com/
    │       └── devi/
    │           └── project/
    │               ├── ProjectApplication.java
    │               │
    │               ├── controller/
    │               │   ├── ProductController.java
    │               │   ├── Registration.java
    │               │   └── LoginController.java
    │               │
    │               ├── model/
    │               │   ├── Product.java
    │               │   └── User.java
    │               │
    │               ├── repository/
    │               │   ├── ProductRepo.java
    │               │   └── UserRepo.java
    │               │
    │               ├── service/
    │               │   └── ProductService.java
    │               │
    │               ├── exception/
    │               │   ├── ExceptionManager.java
    │               │   └── UserAlreadyExit.java
    │               │
    │               └── security/
    │                   ├── SecurityConfig.java
    │                   ├── UserService.java
    │                   ├── UserRegister.java
    │                   ├── JwtService.java
    │                   ├── JwtFilter.java
    │                   └── DataSeeder.java
    │
    └── resources/
        └── application.properties

API Endpoints

Base URL

/api/v1

Public Endpoints

Application Information

GET /api/v1/info

Returns application information.

Register User

POST /register

Example request:

{
  "username": "john",
  "password": "password123"
}

New users are registered with the USER role.

Login

POST /login

Example request:

{
  "username": "admin",
  "password": "********"
}

Successful login returns a JWT token.

Security note: Do not publish real development or production passwords in a public repository. Keep credentials in secure environment configuration.

Product Endpoints

Method

Endpoint

Access

Description

GET

/api/v1/products?page=0&size=10

USER, ADMIN

Get products with pagination

GET

/api/v1/product/{id}

USER, ADMIN

Get product by ID

POST

/api/v1/product

ADMIN

Add a product

GET

/api/v1/product/{id}/image

USER, ADMIN

Get product image

PUT

/api/v1/product/{id}

ADMIN

Update a product

PATCH

/api/v1/product/{id}

ADMIN

Partially update a product

DELETE

/api/v1/product/{id}

ADMIN

Delete a product

GET

/api/v1/products/search?keyword=phone&page=0&size=10

USER, ADMIN

Search products

Add Product

POST /api/v1/product

Accessible by: ADMIN

Uses:

multipart/form-data

The request contains product data and a product image.

Update Product

PUT /api/v1/product/{id}

Updates the product and optionally replaces the image.

Partial Update

PATCH /api/v1/product/{id}

Currently updates:

price

quantity

Search Products

GET /api/v1/products/search?keyword=phone&page=0&size=10

Searches products by:

name

brand

category

Search is case-insensitive.

Authentication & Authorization

This project uses JWT-based stateless authentication.

Authentication Flow

User
 ↓
Login
 ↓
AuthenticationManager
 ↓
UserDetailsService
 ↓
UserRepository
 ↓
Password verification using BCrypt
 ↓
JWT generated
 ↓
JWT returned to client

For subsequent requests:

Client
 ↓
Authorization: Bearer <JWT>
 ↓
JwtFilter
 ↓
JWT validation
 ↓
Extract username
 ↓
Load UserDetails
 ↓
Create Authentication
 ↓
SecurityContextHolder
 ↓
Role authorization
 ↓
Controller

Roles & Permissions

Operation

USER

ADMIN

View products

✅

✅

Search products

✅

✅

View product image

✅

✅

Add product

❌

✅

Update product

❌

✅

Partial update

❌

✅

Delete product

❌

✅

Password Security

Passwords are never stored as plain text.

During registration:

Plain Password
      ↓
BCryptPasswordEncoder
      ↓
Encrypted/hashed password
      ↓
Database

During login, Spring Security verifies the entered password against the stored BCrypt hash.

Default Admin

The application contains a startup data seeder that creates an admin user if one does not already exist.

The development/demo admin credentials are configured by the application's DataSeeder.

Important: Do not commit real passwords or production credentials to GitHub. Use environment variables or another secure configuration mechanism.

Database Configuration

The application uses MySQL.

Example configuration:

spring.datasource.url=jdbc:mysql://localhost:3306/productdb
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}

Create the database before starting the application:

CREATE DATABASE productdb;

Hibernate is configured to update the database schema:

spring.jpa.hibernate.ddl-auto=update

Environment Variables

Sensitive values should be supplied through environment variables.

Example:

DB_PASSWORD=your_database_password

The JWT secret should also be kept outside source control in a production environment.

Running the Project Locally

Prerequisites

Install:

Java 17+ (or the Java version configured by the project)

Maven

MySQL

Git

Check Java:

java -version

Check Maven:

mvn -version

1. Clone the Repository

git clone https://github.com/Devi12655/smart_product_management_system.git

Navigate into the project:

cd smart_product_management_system

2. Create MySQL Database

CREATE DATABASE productdb;

3. Configure Database Password

Set the environment variable expected by application.properties.

Windows PowerShell:

$env:DB_PASSWORD="your_password"

Linux/macOS:

export DB_PASSWORD=your_password

4. Start the Application

Using Maven:

mvn spring-boot:run

On Windows, using the Maven wrapper:

mvnw.cmd spring-boot:run

Or run the main Spring Boot class:

ProjectApplication.java

Testing With Postman

A typical flow is:

1. Register a user
        ↓
2. Login
        ↓
3. Copy JWT token
        ↓
4. Add token to Authorization header
        ↓
5. Call protected product APIs

Authorization header:

Authorization: Bearer <your-jwt-token>

For admin operations, use an account that has the ADMIN role.

Product Example

A product contains fields such as:

{
  "name": "Galaxy Phone",
  "description": "Smart phone",
  "brand": "Samsung",
  "price": 49999,
  "category": "Electronics",
  "releaseDate": "2026-01-15",
  "quantity": 10
}

Images are handled separately through multipart upload and stored as binary data.

Validation

Product fields use Bean Validation.

Examples:

price > 0

quantity >= 1

Invalid requests return:

400 Bad Request

Validation errors are handled centrally through @ControllerAdvice.

Exception Handling

The project uses centralized exception handling with:

@ControllerAdvice

It handles:

validation errors

general exceptions

application-specific exceptions

Important HTTP responses include:

Status

Meaning

200

OK

201

CREATED

400

BAD REQUEST

401

UNAUTHORIZED

403

FORBIDDEN

404

NOT FOUND

500

INTERNAL SERVER ERROR

401 vs 403

401 Unauthorized

The user is not properly authenticated.

Examples:

No JWT

Invalid JWT

Invalid username/password

403 Forbidden

The user is authenticated but does not have permission.

Example:

USER tries to delete a product

Pagination

Product APIs support pagination.

Example:

GET /api/v1/products?page=0&size=10

Where:

page = page number (zero-based)

size = number of records per page

Spring Data returns a Page<Product> containing the data and pagination metadata.

Search

The search endpoint uses a custom JPQL query.

GET /api/v1/products/search?keyword=phone

The keyword is searched against:

name

brand

category

The query uses LOWER() and LIKE to provide case-insensitive partial matching.

Image Handling

Product images are uploaded using:

multipart/form-data

The project stores:

imageName

imageType

imageData

The image binary is stored in the database using:

@Lob
private byte[] imageData;

Configured image size:

5 MB

Key Spring Boot Concepts Demonstrated

Spring Boot

@SpringBootApplication

Dependency Injection

Configuration

Application properties

Component scanning

REST API

@RestController

@RequestMapping

@GetMapping

@PostMapping

@PutMapping

@PatchMapping

@DeleteMapping

@PathVariable

@RequestParam

@RequestBody

@RequestPart

ResponseEntity

JPA / Hibernate

@Entity

@Id

@GeneratedValue

@Table

@Column

@Lob

JpaRepository

JPQL

Derived queries

Pagination

Spring Security

SecurityFilterChain

UserDetails

UserDetailsService

AuthenticationManager

PasswordEncoder

BCrypt

SecurityContextHolder

Role-based authorization

Stateless security

JWT

JWT generation

JWT signature

Bearer token

JWT validation

Custom JWT filter

Validation

@Valid

@Positive

@Min

MethodArgumentNotValidException

Exception Handling

@ControllerAdvice

@ExceptionHandler

Custom exceptions

HTTP error responses

Testing

The project contains a Spring Boot application-context test.

Recommended future test coverage:

Controller tests

Service tests

Repository tests

Security tests

JWT tests

Integration tests

Validation tests

Future Enhancements

The current project is suitable as a learning/demo backend. Possible improvements include:

Use DTOs instead of exposing entities directly

Add a global structured error response

Return 409 Conflict for duplicate usernames

Add consistent image-size validation during product creation

Add JWT expiration

Improve JWT error handling

Move JWT secret to secure environment configuration

Move admin credentials to secure configuration

Add unit tests

Add integration tests

Add API documentation with Swagger/OpenAPI

Add logging instead of System.out.println

Add database migrations using Flyway or Liquibase

Store large images in object/file storage instead of the database

Add sorting and advanced filtering

Add refresh tokens if required

Add Docker support

Add CI/CD pipeline

GitHub Security

Before pushing the project, make sure sensitive information is not committed.

Do not commit:

.env
real database passwords
production JWT secrets
private credentials
IDE-specific files
target/

A .gitignore should include items such as:

target/
.idea/
.vscode/
*.iml
.env
*.log

Git Commands

Initialize Git:

git init

Add files:

git add .

Commit:

git commit -m "Initial commit"

Add GitHub remote:

git remote add origin https://github.com/Devi12655/smart_product_management_system.git

Push:

git branch -M main
git push -u origin main

Author

Devi Sri
