# 📚 BookUserApp

A Spring Boot RESTful web application that manages **Books** and **Users** with full CRUD operations. It includes input validation, exception handling, and unit tests using JUnit and Mockito.

---

## 🚀 Features

- Manage **Books**: title, author, ISBN, published date.
- Manage **Users**: name, email, registration date.
- RESTful API design using Spring MVC.
- Input validation using Jakarta Bean Validation.
- Custom exceptions like `BookNotFoundException` and `UserNotFoundException`.
- Unit testing with JUnit 5 and Mockito for service layer.
- Layered architecture (Controller → Service → Repository).

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot 3.5.0
- Hibernate - It is an ORM framework that maps java objects to database tables and handles CRDUD operations.
- Jakarta Validation : provide annotations (like @NotNull) to validate Java bean properties.
- Mockito : It is a mocking framework, which is used for writing test units
- Maven : A buid tool primarliy used for Javaprojects.

---

## 📂 Project Structure

├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/BookUserApp/
│   │   │       ├── BookUserAppApplication.java
│   │   │       ├── controller/
│   │   │       │   ├── BookController.java
│   │   │       │   └── UserController.java
│   │   │       ├── exception/
│   │   │       │   ├── BookNotFoundException.java
│   │   │       │   ├── GlobalExceptionHandler.java
│   │   │       │   └── UserNotFoundException.java
│   │   │       ├── model/
│   │   │       │   ├── Book.java
│   │   │       │   └── User.java
│   │   │       ├── repository/
│   │   │       │   ├── BookRepository.java
│   │   │       │   └── UserRepository.java
│   │   │       └── service/
│   │   │           ├── BookService.java
│   │   │           └── UserService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── (any other templates or static files)
│
│   └── test/
│       └── java/
│           └── com/example/BookUserApp/
│               ├── BookUserAppApplicationTests.java
│               ├── controller/
│               │   ├── BookControllerTest.java
│               │   └── UserControllerTest.java
│               └── service/
│                   ├── BookServiceTest.java
│                   └── UserServiceTest.java

