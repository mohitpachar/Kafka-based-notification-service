# 🚀 Kafka-Based Real-Time Notification System

A scalable **Event-Driven Notification System** built using **Spring Boot, Apache Kafka, Redis, and WebSocket**. The project demonstrates asynchronous communication between microservices, real-time browser notifications, HTML email delivery, and live system monitoring.

---

## 📌 Overview

This project implements a **Kafka-Based Real-Time Notification System** using a **microservices architecture**.

The system consists of two independent Spring Boot services that communicate asynchronously through **Apache Kafka**. Whenever an e-commerce event (Order Placed, Payment Completed, Shipment Update) is triggered from the **ShopFlow Event Dashboard**, the Event Service publishes the event to Kafka.

The Notification Service consumes the event, stores it in **Redis**, instantly pushes the notification to the browser using **WebSocket**, and simultaneously sends a formatted **HTML email** using Gmail SMTP.

The project also includes a **real-time Monitoring Dashboard** powered by Spring Boot Actuator and custom metrics, displaying service health, Kafka message statistics, email delivery statistics, and processing performance.

---

# 🎯 Objectives

- Build a scalable event-driven notification system
- Implement asynchronous communication using Apache Kafka
- Deliver real-time browser notifications using WebSocket
- Store notifications efficiently using Redis
- Send HTML email notifications using Gmail SMTP
- Improve reliability using Retry Mechanism
- Handle failures using Dead Letter Topic (DLT)
- Monitor application health and metrics in real time

---

# 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 17 | Programming Language |
| Spring Boot | Backend Framework |
| Apache Kafka | Event Streaming Platform |
| Spring Kafka | Kafka Integration |
| Redis (Memurai) | Notification Storage |
| Spring Data Redis | Redis Operations |
| Spring WebSocket (STOMP + SockJS) | Real-Time Notification Delivery |
| Spring Mail | HTML Email Notifications |
| Spring Boot Actuator | Monitoring & Health Checks |
| HTML | Frontend |
| CSS | Styling |
| JavaScript | Client-side Logic |
| Maven | Dependency Management |

---

# 🏗️ System Architecture

```
                     ShopFlow Dashboard
                            │
                            ▼
                  Event Service (8082)
                            │
                            ▼
                     Apache Kafka Broker
                            │
                            ▼
              Notification Service (8081)
        ┌──────────────┬───────────────┬─────────────┐
        ▼              ▼               ▼             ▼
     Redis        WebSocket      Gmail SMTP    Metrics Service
        │              │               │
        ▼              ▼               ▼
 Notification     Browser UI      HTML Email
     History
        │
        ▼
 Monitoring Dashboard
```

---

# 🔄 Complete Workflow

1. User triggers an event from the ShopFlow Dashboard.
2. Event Service creates an event message.
3. Event is published to the Kafka topic.
4. Kafka stores the message.
5. Notification Service consumes the event.
6. Event is converted into a notification.
7. Notification is stored in Redis.
8. WebSocket pushes the notification instantly to the browser.
9. HTML email is sent through Gmail SMTP.
10. Metrics Dashboard updates automatically with processing statistics.

---

# 🏢 Microservices

## Event Service (Port 8082)

Responsible for:

- Generating business events
- Publishing messages to Kafka
- Providing REST APIs
- ShopFlow Event Dashboard

### APIs

- POST `/api/events/order`
- POST `/api/events/payment`
- POST `/api/events/shipment`
- GET `/api/events/health`

---

## Notification Service (Port 8081)

Responsible for:

- Kafka Consumer
- Notification Processing
- Redis Storage
- WebSocket Notifications
- HTML Email Delivery
- Monitoring Metrics
- Retry Mechanism
- Dead Letter Topic (DLT)

### APIs

- POST `/auth/login`
- GET `/auth/me`
- GET `/notifications`
- PUT `/notifications/read/{id}`
- DELETE `/notifications/delete/{id}`
- GET `/metrics/summary`

---

# ✨ Features

- ⚡ Event-Driven Microservices Architecture
- 📨 Apache Kafka Asynchronous Messaging
- ⚡ Real-Time Browser Notifications
- 🔔 WebSocket Notification Delivery
- 💾 Redis Notification Storage
- 📧 HTML Email Notifications using Gmail SMTP
- 📜 Notification History
- ✅ Mark Notification as Read
- 🗑️ Delete Notifications
- 🔁 Retry Mechanism
- 🚫 Dead Letter Topic (DLT)
- 📊 Live Monitoring Dashboard
- ❤️ Spring Boot Actuator Health Monitoring
- 📈 Kafka Custom Metrics
- 🔄 Auto Refresh Dashboard

---

# 📡 REST API Summary

## Event Service

| Method | Endpoint |
|---------|----------|
| POST | /api/events/order |
| POST | /api/events/payment |
| POST | /api/events/shipment |
| GET | /api/events/health |

---

## Notification Service

| Method | Endpoint |
|---------|----------|
| POST | /auth/login |
| GET | /auth/me |
| GET | /notifications |
| PUT | /notifications/read/{id} |
| DELETE | /notifications/delete/{id} |
| GET | /metrics/summary |

---

# 📊 Monitoring Dashboard

The Monitoring Dashboard provides live insights into the system.

It displays:

- Service Health
- Kafka Messages Processed
- Event Type Statistics
- Email Delivery Count
- Average Processing Time
- Dead Letter Count
- Activity Logs

Dashboard refreshes automatically every **5 seconds**.

---

# 📈 Performance Results

| Metric | Performance |
|---------|-------------|
| Kafka Processing Time | 4–16 ms |
| WebSocket Notification | < 50 ms |
| Redis Read Time | < 5 ms |
| HTML Email Delivery | 4–5 seconds |
| Dead Letter Messages | 0 during testing |

---

# 📂 Project Structure

```
Kafka-Based-Notification-System
│
├── event-service
│   ├── controller
│   ├── service
│   ├── kafka
│   └── model
│
├── notification-service
│   ├── consumer
│   ├── controller
│   ├── websocket
│   ├── redis
│   ├── email
│   ├── metrics
│   └── dlt
│
└── README.md
```

---

# 🚀 Getting Started

## Prerequisites

- Java 17
- Maven
- Apache Kafka
- Redis (Memurai)
- Eclipse STS / IntelliJ IDEA

---

## Clone Repository

```bash
git clone https://github.com/mohitpachar/Kafka-based-notification-service.git
```

---

## Start Kafka

Start:

- Zookeeper (if required)
- Kafka Broker

Create topic:

```bash
ecommerce-events
```

---

## Start Redis

Start Memurai/Redis Server.

---

## Run Services

Start:

1. Event Service (Port 8082)

2. Notification Service (Port 8081)

---

# 📷 Project Screenshots

### ShopFlow Dashboard

_Add Screenshot_

---

### Notification Dashboard

_Add Screenshot_

---

### Monitoring Dashboard

_Add Screenshot_

---

# 📚 Learning Outcomes

- Event-Driven Architecture
- Apache Kafka Producer & Consumer
- Spring Boot Microservices
- Redis Integration
- WebSocket Communication
- Spring Mail Integration
- Retry & Dead Letter Topic
- Spring Boot Actuator
- Custom Metrics Collection
- Distributed Systems Concepts

---

# 🔮 Future Enhancements

- Push Notifications
- SMS Notifications
- User Notification Preferences
- Kafka Streams Analytics
- Prometheus Integration
- Grafana Dashboard
- MySQL/PostgreSQL for permanent notification storage
- Notification Analytics

---

# 👨‍💻 Author

**Mohit Pachar**

B.Tech – Computer Science & Engineering

Central University of Rajasthan

GitHub: https://github.com/mohitpachar

---

## ⭐ If you found this project useful, don't forget to star the repository!