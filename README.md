# 💳 Enterprise Payment Orchestrator

An enterprise-grade Payment Orchestrator built using **Java 17**, **Spring Boot**, and **PostgreSQL**. The application simulates how modern payment systems intelligently route transactions between multiple payment gateways while ensuring reliability, fault tolerance, and complete auditability.

---

## 🚀 Features

### Core Payment Operations
- Create Payment
- Payment Authorization
- Payment Capture
- Payment Refund

### Enterprise Features
- Idempotency Support
- Intelligent Gateway Routing
- Automatic Gateway Failover
- Circuit Breaker Pattern
- Payment State Machine
- Audit Logging
- Transaction Timeline API
- Webhook Processing
- Reconciliation Scheduler
- Metrics Dashboard API
- Global Exception Handling

---

# 🏗 Architecture

```
                        Client
                           │
                           ▼
                  Payment Controller
                           │
                           ▼
                  Payment Service
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
        ▼                  ▼                  ▼
 Routing Service    Circuit Breaker    State Machine
        │                  │                  │
        └──────────────┬───┴──────────────────┘
                       ▼
                 Gateway Router
                 ┌──────────────┐
                 │              │
                 ▼              ▼
          Razorpay Gateway  Stripe Gateway
                 │              │
                 └──────┬───────┘
                        ▼
                  PostgreSQL Database
```

---

# 🛠 Tech Stack

| Technology | Usage |
|------------|------|
| Java 17 | Backend |
| Spring Boot 4.1 | REST APIs |
| Spring Data JPA | Database Access |
| Spring Security | Security |
| PostgreSQL | Database |
| Hibernate | ORM |
| Maven | Build Tool |
| Postman | API Testing |

---

# 📂 Project Structure

```
src
 ├── controller
 ├── dto
 ├── entity
 ├── enums
 ├── gateway
 ├── repository
 ├── routing
 ├── scheduler
 ├── security
 ├── service
 ├── statemachine
 ├── webhook
 ├── exception
 └── PaymentOrchestratorApplication
```

---

# 💾 Database

### Transactions

Stores payment information.

- Transaction ID
- Amount
- Currency
- Status
- Created Time

---

### Idempotency Records

Prevents duplicate payment creation.

---

### Transaction State Log

Maintains complete audit history.

- From State
- To State
- Event
- Timestamp
- Metadata

---

# 🔄 Payment Workflow

```
Create Payment
      │
      ▼
Created
      │
      ▼
Authorization Initiated
      │
      ▼
Authorized
      │
      ▼
Capture Initiated
      │
      ▼
Captured
      │
      ▼
Refund Initiated
      │
      ▼
Refunded
```

---

# 📡 API Endpoints

## Create Payment

```
POST /payments
```

---

## Capture Payment

```
POST /payments/{transactionId}/capture
```

---

## Refund Payment

```
POST /payments/{transactionId}/refund
```

---

## Transaction Timeline

```
GET /payments/{transactionId}/timeline
```

---

## Metrics

```
GET /metrics
```

---

## Webhook

```
POST /webhooks/razorpay
```

---

## Circuit Breaker Reset

```
POST /circuit/reset
```

---

# 📊 Enterprise Features

### Intelligent Routing

- USD → Stripe
- High-value payments → Stripe
- INR → Razorpay

---

### Automatic Failover

If Razorpay fails:

```
Razorpay
     │
     ▼
Stripe
```

---

### Circuit Breaker

After three consecutive gateway failures:

- Opens the circuit
- Skips failed gateway
- Routes traffic to backup gateway

---

### Audit Logging

Every payment event is recorded.

Example:

```
AUTH_SUCCESS

CAPTURE_SUCCESS

REFUND_SUCCESS
```

---

### Timeline API

Returns complete transaction history.

Example:

```
GET /payments/{id}/timeline
```

---

### Metrics API

Returns live payment statistics.

Example:

```json
{
  "totalTransactions": 7,
  "authorized": 5,
  "captured": 1,
  "refunded": 1
}
```
---

# Architecture

The system architecture is shown below.

![Architecture](docs/architecture.png)
---

### Reconciliation Scheduler

Runs periodically to identify pending or inconsistent transactions requiring reconciliation.

---

# ▶️ Running the Project

Clone the repository

```bash
git clone <repository-url>
```

Navigate to the project

```bash
cd payment-orchestrator
```

Run PostgreSQL.

Configure `application.properties`.

Run the application.

```bash
mvn spring-boot:run
```

Server starts at

```
http://localhost:8080
```

---

# 🧪 Testing

All APIs were tested using Postman.

Implemented and verified:

- Payment Creation
- Authorization
- Capture
- Refund
- Timeline API
- Metrics API
- Webhooks
- Circuit Breaker
- Reconciliation
- Exception Handling

---

# 📈 Future Improvements

- JWT Authentication
- Redis Caching
- Kafka Event Streaming
- Docker Deployment
- Kubernetes Deployment
- Prometheus Monitoring
- Grafana Dashboard
- Real Payment Gateway Integration

---

# 👩‍💻 Author

Nishita Kumari

Computer Science Engineering (Data Science)

Built as an enterprise backend project demonstrating payment orchestration, fault tolerance, and distributed system design using Spring Boot.
