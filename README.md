# E-Commerce Microservices

A scalable e-commerce microservices architecture built with **Java 21** and **Spring Boot 3.4**. This project demonstrates best practices for building distributed systems with microservices patterns including service discovery, API gateway routing, and inter-service communication.

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Services](#running-the-services)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)
- [License](#license)

## 🎯 Overview

This e-commerce microservices platform is designed to provide a modular, scalable, and maintainable solution for online retail operations. Each service handles a specific business domain and communicates through the API Gateway.

**Key Features:**
- Microservices-based architecture with Spring Boot
- Service Discovery using Netflix Eureka
- Dynamic API Gateway routing (`lb://` load-balanced URIs)
- RESTful CRUD APIs for all domains
- H2 in-memory database for development, PostgreSQL for production
- Docker Compose for local infrastructure setup

## 🏗️ Architecture

```
                    ┌──────────────────┐
                    │ Discovery Service│  (Eureka Server, :8761)
                    └────────▲─────────┘
                             │ registers
    ┌────────────────────────┼────────────────────────┐
    │                        │                        │
┌───┴──────┐  ┌──────────────┴───────────┐  ┌────────┴─────────┐
│API Gateway│  │     E-Commerce Services  │  │  Infrastructure  │
│  :8080    │  │                          │  │                  │
│           │  │  user-service     :8081  │  │  PostgreSQL :5432│
│ lb://  ───┼──│  product-service  :8082  │  │  RabbitMQ   :5672│
│ routing   │  │  order-service    :8083  │  │                  │
│           │  │  inventory-service:8084  │  │                  │
└───────────┘  │  payment-service  :8085  │  └──────────────────┘
               └──────────────────────────┘
```

## 📁 Project Structure

```
java-spring-microservices/
│
├── discovery-service/         # Eureka Service Registry (port 8761)
│   └── src/
│
├── api-gateway/               # API Gateway with dynamic routing (port 8080)
│   └── src/
│
├── user-service/              # User management & JWT auth (port 8081)
│   └── src/
│
├── product-service/           # Product catalog (port 8082)
│   ├── src/.../model/Product.java
│   ├── src/.../repository/ProductRepository.java
│   └── src/.../controller/ProductController.java
│
├── order-service/             # Order processing (port 8083)
│   ├── src/.../model/Order.java, OrderItem.java
│   ├── src/.../repository/OrderRepository.java
│   └── src/.../controller/OrderController.java
│
├── inventory-service/         # Inventory management (port 8084)
│   ├── src/.../model/Inventory.java
│   ├── src/.../repository/InventoryRepository.java
│   └── src/.../controller/InventoryController.java
│
├── payment-service/           # Payment processing (port 8085)
│   ├── src/.../model/Payment.java
│   ├── src/.../repository/PaymentRepository.java
│   └── src/.../controller/PaymentController.java
│
├── pom.xml                    # Parent POM (multi-module aggregator)
└── docker-compose.yml         # PostgreSQL & RabbitMQ for local dev
```

## 🛠️ Technologies

- **Framework:** Spring Boot 3.4.1
- **Language:** Java 21
- **Build Tool:** Maven (multi-module)
- **Service Discovery:** Netflix Eureka
- **API Gateway:** Spring Cloud Gateway
- **Database:** H2 (dev) / PostgreSQL (prod)
- **Message Queue:** RabbitMQ
- **Container:** Docker & Docker Compose

## ✅ Prerequisites

Before you begin, ensure you have the following installed:

- Java JDK 21 or higher
- Maven 3.6 or higher
- Docker & Docker Compose
- Git
- IDE: IntelliJ IDEA or VS Code with Java extensions

## 🚀 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/JateenDhaduk/e-com-mircoservice.git
cd e-com-mircoservice
```

### 2. Navigate to the Project

```bash
cd java-spring-microservices
```

### 3. Build the Project

```bash
mvn clean install
```

### 4. Start Infrastructure (PostgreSQL & RabbitMQ)

```bash
docker-compose up -d
```

## 📦 Running the Services

### Start Order (Important!)

**Always start the Discovery Service first**, then the Gateway, then the business services.

```bash
# Terminal 1: Discovery Service (must start first)
cd discovery-service && mvn spring-boot:run

# Terminal 2: API Gateway
cd api-gateway && mvn spring-boot:run

# Terminal 3-7: Business Services (any order)
cd user-service && mvn spring-boot:run
cd product-service && mvn spring-boot:run
cd order-service && mvn spring-boot:run
cd inventory-service && mvn spring-boot:run
cd payment-service && mvn spring-boot:run
```

### Verify Services are Running

- **Eureka Dashboard:** `http://localhost:8761` — shows all registered services
- **Health checks:**

```bash
curl http://localhost:8080/actuator/health   # API Gateway
curl http://localhost:8081/actuator/health   # User Service
curl http://localhost:8082/actuator/health   # Product Service
curl http://localhost:8083/actuator/health   # Order Service
curl http://localhost:8084/actuator/health   # Inventory Service
curl http://localhost:8085/actuator/health   # Payment Service
```

## 📚 API Documentation

All requests go through the **API Gateway** on port `8080`:

### Product Service

```
GET    /api/products              - Get all products
GET    /api/products/{id}         - Get product by ID
GET    /api/products/category/{c} - Get products by category
GET    /api/products/search?name= - Search products by name
POST   /api/products              - Create new product
PUT    /api/products/{id}         - Update product
DELETE /api/products/{id}         - Delete product
```

### Order Service

```
GET    /api/orders                - Get all orders
GET    /api/orders/{id}           - Get order by ID
GET    /api/orders/user/{userId}  - Get orders by user
POST   /api/orders                - Create new order
PUT    /api/orders/{id}/status    - Update order status
DELETE /api/orders/{id}           - Cancel order
```

### Inventory Service

```
GET    /api/inventory                         - Get all inventory
GET    /api/inventory/product/{productId}     - Get stock for product
GET    /api/inventory/product/{id}/in-stock   - Check if in stock
POST   /api/inventory                         - Add inventory record
PUT    /api/inventory/product/{id}/quantity   - Update stock quantity
```

### Payment Service

```
GET    /api/payments              - Get all payments
GET    /api/payments/{id}         - Get payment by ID
GET    /api/payments/order/{id}   - Get payment for an order
POST   /api/payments              - Process a payment
POST   /api/payments/{id}/refund  - Refund a payment
```

### User Service

```
POST   /api/users/register        - Register a new user
POST   /api/users/login           - Login and get JWT token
GET    /api/users/{id}            - Get user by ID
```

## 🧪 Testing

Run tests for all services:

```bash
mvn test
```

Run tests for a specific service:

```bash
cd product-service
mvn test
```

## 📝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit changes: `git commit -am 'Add new feature'`
4. Push to branch: `git push origin feature/your-feature`
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

**Jateen Dhaduk**

- GitHub: [@JateenDhaduk](https://github.com/JateenDhaduk)
- Email: jateendhaduk456@gmail.com

---

**Last Updated:** March 2026

**Version:** 2.0.0
