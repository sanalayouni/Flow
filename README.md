# 🚀 ContentFlow — Social Media Content Planner API

## 📌 Overview

ContentFlow is a backend SaaS application designed to help small businesses, creators, and freelancers **plan, organize, and manage their social media content** efficiently.

The system provides a structured REST API to create, categorize, and schedule posts, making content management simple and consistent.

---

## 🎯 Problem

Small businesses often struggle with:

- Lack of content organization
- Inconsistent posting schedules
- Difficulty planning ahead
- Managing ideas across multiple platforms

This leads to poor engagement and a weak online presence.

---

## 💡 Solution

ContentFlow provides a **simple and scalable backend system** that allows users to:

- Organize content using categories
- Create and manage posts with rich metadata
- Schedule content in advance
- Maintain a consistent content strategy
- Filter and search through content efficiently

---

## 👥 Target Users

- Small businesses
- Freelancers
- Personal brands
- Social media managers

---

## ⚙️ Features (MVP)

### 🔐 Authentication
- Register user
- Login user (JWT-based)

### 🗂️ Category Management
- Create category
- View all categories
- View single category
- Update category
- Delete category (with post validation)

### 📝 Post Management
- Create post
- Update post
- Delete post (soft delete)
- View all posts (with pagination)
- View single post

### 📅 Scheduling
- Assign scheduled date to posts
- Manage post status:
  - `DRAFT`
  - `SCHEDULED`
  - `PUBLISHED`
  - `ARCHIVED`

### 🔍 Filtering & Pagination
- Filter posts by status
- Filter posts by category
- Paginated responses on all list endpoints

---

## 🔄 Application Flow

1. User registers and logs in → receives JWT token
2. User creates categories (e.g., "Instagram Tips", "Product Launch")
3. User creates posts under categories
4. User sets post status and schedule date
5. User filters and manages posts
6. User publishes or archives posts

---

## 🧱 Tech Stack

| Layer        | Technology                     |
|--------------|--------------------------------|
| Backend      | Spring Boot 3.x               |
| Language     | Java 17+                       |
| Database     | MySQL 8                        |
| ORM          | Spring Data JPA / Hibernate    |
| Security     | Spring Security + JWT          |
| Validation   | Jakarta Bean Validation        |
| Docs         | SpringDoc OpenAPI (Swagger)    |
| Testing      | JUnit 5 + Mockito              |
| Tools        | Postman, Git, Maven            |

---

## 🏗️ Project Structure

com.contentflow
│
├── controller/ # REST controllers (API layer)
├── service/ # Business logic
├── repository/ # Data access (Spring Data JPA)
├── model/ # JPA entities
├── dto/ # Request & Response DTOs
│ ├── request/
│ └── response/
├── mapper/ # Entity ↔ DTO mapping
├── exception/ # Global exception handling
├── security/ # JWT filter, SecurityConfig
└── config/ # App configuration


---

## 📡 API Endpoints

### 🔐 Auth
| Method | Endpoint         | Description       |
|--------|------------------|-------------------|
| POST   | `/auth/register` | Register new user |
| POST   | `/auth/login`    | Login & get JWT   |

### 🗂️ Categories
| Method | Endpoint            | Description          |
|--------|---------------------|----------------------|
| POST   | `/categories`       | Create category      |
| GET    | `/categories`       | List all categories  |
| GET    | `/categories/{id}`  | Get single category  |
| PUT    | `/categories/{id}`  | Update category      |
| DELETE | `/categories/{id}`  | Delete category      |

### 📝 Posts
| Method | Endpoint        | Description                |
|--------|-----------------|----------------------------|
| POST   | `/posts`        | Create post                |
| GET    | `/posts`        | List posts (paginated)     |
| GET    | `/posts/{id}`   | Get single post            |
| PUT    | `/posts/{id}`   | Update post                |
| DELETE | `/posts/{id}`   | Soft delete post           |

### 🔍 Filters (query parameters on `GET /posts`)
| Parameter    | Example                        |
|--------------|--------------------------------|
| `status`     | `GET /posts?status=DRAFT`      |
| `categoryId` | `GET /posts?categoryId=1`      |
| `page`       | `GET /posts?page=0&size=10`    |

---

## 🗃️ Database Design

### User
| Column      | Type         | Constraints              |
|-------------|--------------|--------------------------|
| id          | BIGINT       | PK, AUTO_INCREMENT       |
| username    | VARCHAR(50)  | NOT NULL, UNIQUE         |
| email       | VARCHAR(100) | NOT NULL, UNIQUE         |
| password    | VARCHAR(255) | NOT NULL (BCrypt hashed) |
| created_at  | TIMESTAMP    | NOT NULL                 |
| updated_at  | TIMESTAMP    | NOT NULL                 |

### Category
| Column      | Type         | Constraints              |
|-------------|--------------|--------------------------|
| id          | BIGINT       | PK, AUTO_INCREMENT       |
| name        | VARCHAR(100) | NOT NULL                 |
| user_id     | BIGINT       | FK → User(id), NOT NULL  |
| created_at  | TIMESTAMP    | NOT NULL                 |
| updated_at  | TIMESTAMP    | NOT NULL                 |

### Post
| Column         | Type         | Constraints                |
|----------------|--------------|----------------------------|
| id             | BIGINT       | PK, AUTO_INCREMENT         |
| title          | VARCHAR(200) | NOT NULL                   |
| content        | TEXT         | NOT NULL                   |
| status         | ENUM         | DRAFT, SCHEDULED, PUBLISHED, ARCHIVED |
| scheduled_date | TIMESTAMP    | NULLABLE                   |
| user_id        | BIGINT       | FK → User(id), NOT NULL    |
| category_id    | BIGINT       | FK → Category(id), NULLABLE |
| created_at     | TIMESTAMP    | NOT NULL                   |
| updated_at     | TIMESTAMP    | NOT NULL                   |

### ER Relationships

User (1) ──── (N) Category
User (1) ──── (N) Post
Category (1) ── (N) Post


---

## 🚀 Development Roadmap

### Phase 1 — Project Setup & Database
- [ ] Initialize Spring Boot project (Spring Initializr)
- [ ] Configure MySQL connection
- [ ] Create User, Category, Post entities with relationships
- [ ] Verify tables are auto-generated

### Phase 2 — Categories CRUD
- [ ] Build Category DTOs (request/response)
- [ ] Implement CategoryService
- [ ] Implement CategoryController
- [ ] Add validation
- [ ] Test with Postman

### Phase 3 — Posts CRUD + Filtering
- [ ] Build Post DTOs
- [ ] Implement PostService with pagination
- [ ] Implement PostController
- [ ] Add filtering by status and categoryId
- [ ] Add soft delete
- [ ] Test with Postman

### Phase 4 — Authentication
- [ ] Implement user registration with BCrypt
- [ ] Implement login endpoint
- [ ] Add JWT token generation & validation
- [ ] Secure endpoints with Spring Security
- [ ] Test auth flow

### Phase 5 — Polish & Production Readiness
- [ ] Global exception handling (@ControllerAdvice)
- [ ] Input validation (Jakarta Bean Validation)
- [ ] API documentation (SpringDoc OpenAPI)
- [ ] Unit tests (JUnit 5 + Mockito)
- [ ] Integration tests

### Phase 6 — Future Enhancements
- [ ] AI-powered caption generator
- [ ] Hashtag suggestions
- [ ] Best posting time prediction
- [ ] Analytics dashboard
- [ ] Social platform integrations

---

## 📌 Status

🚧 In Development (MVP Phase)

---

## 👨‍💻 Author

**Sana Layouni**
