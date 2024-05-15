<h1 align="center">💰 FinanCerto 💰</h1>

<p align="center">
  <a href="#description">Description</a> • 
  <a href="#functionalities">Functionalities</a> •
  <a href="#technologies">Technologies</a> •
  <a href="#configuration">Installation and Configuration</a> •
  <a href="#endpoints">Endpoints</a> •
  <a href="#author">Author</a>
</p>

<h2 id="description">📋 Description</h2>

Financerto is a Rest API designed to help users manage their personal finances. With Financerto, users can track their expenses, revenues, financial targets and wallet balance easily and conveniently.

<h2 id="functionalities">⚙️ Functionalities</h2>

- 🧑 **User**:
    - Registration of new users.
    - Login with authentication via JWT Token.
- 📉 **Expense**
  - Complete CRUD for expense management.
  - Possibility to add, view, update and delete expenses.
- 📈 **Revenue**
    - Complete CRUD for revenue management.
    - Possibility to add, view, update and delete revenues.
- 🎯 **Target**
    - Complete CRUD for target management.
    - Possibility to add, view, update and delete financial targets.
    - Track target progress in %.
- 💸 **Wallet**
    - Total user balance.
    - Total amount of expenses.
    - Total amount of revenues.

<h2 id="technologies">💻 Technologies</h2>

- Java 17
- Spring Boot 3
- Spring Security
- Flyway
- PostgreSQL
- JUnit and Mockito
- Springdoc

<h2 id="configuration">🛠️ Installation and Configuration</h2>

> You need to have JDK and PostgreSQL installed.

1. Clone Git repository.
```text
git clone https://github.com/rodrigues14/financerto-backend.git
```
2. Check if all dependencies have been installed.
3. Configure database properties in `application.properties` file.
```text
  spring.datasource.url=jdbc:postgresql://localhost/yourDB
  spring.datasource.username=username
  spring.datasource.password=password
```
4. Run the spring boot application.
5. Test the application through postman/Insomnia or Swagger: http://localhost:8080/swagger-ui/index.html#/

<h2 id="endpoints">📍 Endpoints</h2>

### POST / Register user.
**REQUEST**
```json
{
    "name": "Lucas",
    "email": "lucas14@gmail.com",
    "password": "123456"
}
```

### POST / Login.
**REQUEST**
```json
{
    "login": "lucas14@gmail.com",
    "password": "123456"
}
```

**RESPONSE**
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJmaW5hbmNlcnRvIiwic3ViIjoibHVjYXMxNEBnbWFpbC5jb20iLCJleHAiOjE3MTU3ODgzMDR9.6UA9S_BjzaGZYBjkbZQwLl0z_U10A6EzGkUWr4oJ5TM"
}
```

### All endpoints

![swagger](./src/main/resources/static/swagger.png)

<h2 id="author">🧑‍💻 Author</h2>

The Financerto project was developed by [Lucas Rodrigues](https://www.linkedin.com/in/lucas-rodrigues-perfil/).
