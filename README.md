# 📝 Todo Application (Spring Boot)

A simple **Todo Management REST API** built using **Spring Boot** that lets users manage their daily tasks efficiently.  
This project demonstrates the use of **Spring Boot**, **Spring Data JPA**, and **RESTful API design principles** with a clear 3-layered architecture.

---

## 🚀 Features

  -> Create, view, update, and delete Users (CRUD operations)
  -> Create, view, update, and delete Todos (CRUD operations)
  -> Role based Authorization
  -> RESTful API design using Spring Boot
  -> Input validation with annotations
  -> Layered architecture (Controller → Service → Repository)
  -> Security Confuration with JWT Tokens for Authentication & Authorization
  -> Supports any SQL database (MySQL, PostgreSQL, H2, etc.)
  -> Uses Lombok for cleaner code
  -> Includes Swagger annotations for API documentation

---

## 🧠 Tech Stack

   Language => Java 17+ 
   Framework => Spring Boot 
   ORM => Spring Data JPA (Hibernate) 
   Database => Any SQL Database (MySQL , PostgreSQL ,  H2)
   Build Tool => Maven
   Documentation => Swagger , OpenAPI
   Lombok => For boilerplate code reduction

---

## ⚙️ Setup & Configuration

  1️⃣ Clone the Repository
        *bash
        git clone https://github.com/yourusername/TodoApplicationBySpringBoot.git
        cd TodoApplicationBySpringBoot

  2️⃣ Configure the Database
        In the file:
        open => src/main/resources/application.properties

  Use the following template (edit as needed for your database):

    # = DataSource Configuration
    # ===============================
        spring.datasource.url=jdbc:YOUR_DB_TYPE://localhost:YOUR_PORT/YOUR_DB_NAME
        spring.datasource.username=YOUR_USERNAME
        spring.datasource.password=YOUR_PASSWORD

    # Example (PostgreSQL)
        spring.datasource.url=jdbc:postgresql://localhost:5432/todo_db
    
    # = JPA / Hibernate
    # ===============================
        spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
        
  3️⃣ Create a SECRET for JWT Token creation and validations

      MY_SECRET = YOUR SECRET KEY FOR JWT TOKEN MUST BE GREATER THAN 256 BITES 

  BEST PRACTICE => USE A ENVIRONMENT VARIABLE 

    TO REFER   => .env.example file and create a variables in the environment variables 
  
  Build & Run the Application
      mvn clean install
      mvn spring-boot:run
      or run the main class TodoApplication.java directly from your IDE.

  🔗 API Endpoints
        Users Endpoints
             REQUEST                     ENDPOINT                                       PROCESS
        POST                    /users/register                           To register user
        POST                    /users/login                              To login the user and get a JWT Token to hit the request without credentiols
        GET                     /users/account                            To get the user account
        GET                     /users/get                                To get the all users (only admin can GET it)
        PUT                     /users/account/update                     To update user info
        PUT                     /users/account/update/password            To update user Password
        DELETE                  /users/account/delete                     To delete the user account
        DELETE                  /users/delete/{id}                        To delete the user account (only admin can DELETE it)


        Todo Endpoints
             REQUEST                     ENDPOINT                                        PROCESS
        POST                    /todo/create                              To create todo
        GET                     /todo/get                                 To get the all todos of user
        PUT                     /todo/update                              To update todo
        DELETE                  /todo/delete/{id}                         To delete the todo


👨‍💻 Author

Jeeva
Spring Boot Developer (Beginner Level)
https://github.com/jeevaravichandran

⭐ If you like this project, don’t forget to star the repo!

🧾 License

This project is licensed under the MIT License — you are free to use and modify it for learning or personal use.

