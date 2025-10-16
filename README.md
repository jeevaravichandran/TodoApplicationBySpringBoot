# 📝 Todo Application (Spring Boot)

A simple **Todo Management REST API** built using **Spring Boot** that lets users manage their daily tasks efficiently.  
This project demonstrates the use of **Spring Boot**, **Spring Data JPA**, and **RESTful API design** with a clear **3-layered architecture**.

---

## 🚀 Features

- ✅ Create, view, update, and delete **Users (CRUD operations)**  
- ✅ Create, view, update, and delete **Todos (CRUD operations)**  
- 🔐 **Role-based Authorization** (Admin & User roles)  
- 🌐 **RESTful API** design using Spring Boot  
- 🧩 **Input validation** using annotations  
- 🏗️ **Layered architecture** (Controller → Service → Repository)  
- 🔑 **JWT Authentication & Authorization** for secure access  
- 🗄️ Supports **any SQL database** (MySQL, PostgreSQL, H2, etc.)  
- ✨ Uses **Lombok** to reduce boilerplate code  
- 📘 Includes **Swagger annotations** for API documentation  

---

## 🧠 Tech Stack

| Component | Technology |
|------------|-------------|
| **Language** | Java 17+ |
| **Framework** | Spring Boot |
| **ORM** | Spring Data JPA (Hibernate) |
| **Database** | Any SQL Database (MySQL / PostgreSQL / H2) |
| **Build Tool** | Maven |
| **Documentation** | Swagger, OpenAPI |
| **Lombok** | For boilerplate code reduction |

---

## ⚙️ Setup & Configuration

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/yourusername/TodoApplicationBySpringBoot.git
cd TodoApplicationBySpringBoot

2️⃣ Configure the Database

Open the file:
src/main/resources/application.properties

Use the following template (edit as needed for your own database):

# = DataSource Configuration
# ===============================
spring.datasource.url=jdbc:YOUR_DB_TYPE://localhost:YOUR_PORT/YOUR_DB_NAME
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

# Example (PostgreSQL)
# spring.datasource.url=jdbc:postgresql://localhost:5432/todo_db

# = JPA / Hibernate
# ===============================
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update


3️⃣ Configure JWT Secret

Set a secret key for JWT token creation and validation.
It’s best practice to use an environment variable instead of hardcoding.

Example:

MY_SECRET=YOUR_SECRET_KEY_HERE


📌 Note:
Refer to .env.example for format and create environment variables accordingly.

4️⃣ Build & Run the Application
mvn clean install
mvn spring-boot:run


Or run the TodoApplication.java main class directly from your IDE.

🔗 API Endpoints
👤 User Endpoints

| HTTP Method | Endpoint                         | Description                      |
| ----------- | -------------------------------- | -------------------------------- |
| **POST**    | `/users/register`                | Register a new user              |
| **POST**    | `/users/login`                   | Login user and receive JWT Token |
| **GET**     | `/users/account`                 | Get logged-in user details       |
| **GET**     | `/users/get`                     | Get all users (Admin only)       |
| **PUT**     | `/users/account/update`          | Update user information          |
| **PUT**     | `/users/account/update/password` | Update user password             |
| **DELETE**  | `/users/account/delete`          | Delete own account               |
| **DELETE**  | `/users/delete/{id}`             | Delete a user (Admin only)       |

📝 Todo Endpoints
| HTTP Method | Endpoint            | Description                         |
| ----------- | ------------------- | ----------------------------------- |
| **POST**    | `/todo/create`      | Create a new todo                   |
| **GET**     | `/todo/get`         | Get all todos of the logged-in user |
| **PUT**     | `/todo/update`      | Update an existing todo             |
| **DELETE**  | `/todo/delete/{id}` | Delete a todo by ID                 |


🔒 Authentication Flow

Register a user using /users/register

Login via /users/login → Get JWT Token

Use this JWT Token in the Authorization Header for all protected routes
Example:

Authorization: Bearer <your_token_here>


🧩 Project Structure
src/
 ├─ main/
 │   ├─ java/com/example/todo/
 │   │   ├─ controller/   → Handles API endpoints
 │   │   ├─ service/      → Contains business logic
 │   │   ├─ repository/   → Handles data persistence (JPA)
 │   │   ├─ model/        → Entity classes (User, Todo)
 │   │   └─ TodoApplication.java → Main class
 │   └─ resources/
 │       └─ application.properties
 └─ test/
     └─ (Unit tests can be added here)



📘 Example JSON Request

User Registration

{
  "username": "oggy",
  "email": "oggy@gmail.com",
  "password": "oggy@123",
  "name" : "Oggy and the Cockroges"
}


Todo Creation

{
  "name": "Finish Spring Boot project",
  "completed": false
}

🧾 License

This project is licensed under the MIT License —
you are free to use and modify it for learning or personal use.

👨‍💻 Author

Jeeva
Spring Boot Developer (Beginner Level) 💻
https://github.com/jeevaravichandran
