# Job Application Tracking System (ATS) – Backend

A production-grade backend service for a **Job Application Tracking System (ATS)** built with **Spring Boot**, **PostgreSQL**, and **Redis**.
This project models real-world hiring workflows using a **state machine**, **role-based access control**, and **asynchronous background processing**.

---

## 🚀 Objective

Build a robust backend system that goes beyond basic CRUD by implementing:

- Workflow-driven application lifecycle
- Role-Based Access Control (RBAC)
- Asynchronous email notifications
- Clean layered architecture

---

## 🧩 Tech Stack

- Java 17+
- Spring Boot 3.x
- Spring Security
- Spring Data JPA (Hibernate)
- PostgreSQL
- Redis
- Docker & Docker Compose
- Maven
- Lombok

---

## 🏗 Architecture Overview

Controller → Service → Repository → Database  
                     ↓  
                State Machine  
                     ↓  
             Redis Background Worker

---

## 🧑‍💼 Roles & RBAC

| Role | Permissions |
|-----|------------|
| Candidate | Apply for jobs, view own applications |
| Recruiter | Manage jobs, manage applications |
| Hiring Manager | View company applications |

---

## 🔄 Application Workflow

APPLIED → SCREENING → INTERVIEW → OFFER → HIRED  
REJECTED can occur from any stage.

All transitions are validated and logged.

---

## 🔔 Async Notifications

- Redis queue for events
- Background worker processes emails
- API remains non-blocking

---

## ⚙️ Setup Instructions

### Start Infra
```bash
docker compose up -d
```

### Run App
```bash
cd ats-api
mvn clean compile
mvn spring-boot:run
```

App runs at:
```
http://localhost:8081
```

---

## 🐘 Database Access

```bash
docker exec -it job-ats-backend--24a95a1211-postgres-1 psql -U ats_user -d ats
```

---

## 📁 Project Structure

ats-api/
 ├── config/
 ├── controller/
 ├── domain/
 ├── repository/
 ├── security/
 ├── service/
 ├── worker/
 └── AtsApiApplication.java

---

## ✅ Completion Status

✔ All requirements implemented  
✔ Production-ready backend  
✔ Fully documented  