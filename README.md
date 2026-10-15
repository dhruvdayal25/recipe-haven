# Recipe Haven — Online Recipe Sharing Platform

Java web application starter for GUVI Review 1 using Java 17+, Jakarta Servlets/JSP, JDBC, MySQL, Maven, HTML, CSS, and JavaScript.

## Features
- Registration and login with BCrypt password hashing
- Session-based authentication and logout
- Responsive recipe browsing and search
- Create, list, update, and delete your recipes
- Admin approval/rejection for submitted recipes
- Recipe ratings and comments
- JDBC DAO layer, prepared statements, relational database schema, and ER diagram

## Requirements
- JDK 17+
- Apache Maven 3.9+
- MySQL 8+
- Apache Tomcat 10.1+
- VS Code with Extension Pack for Java

## Setup
1. Run `database/schema.sql` in MySQL Workbench or MySQL CLI.
2. Copy `src/main/resources/db.properties.example` to `src/main/resources/db.properties`.
3. Edit the local database username/password in `db.properties`. Never commit real credentials.
4. From the folder containing `pom.xml`, run `mvn clean package`.
5. Deploy `target/recipe-sharing.war` to Tomcat 10.1+.
6. Open `http://localhost:8080/recipe-sharing/`.

## Create an admin
Register through the app, then run this SQL (replace the email):
```sql
USE recipe_sharing;
UPDATE users SET role='ADMIN' WHERE email='your-email@example.com';
```
Sign out and sign in again.

## Review 1 checklist
- [ ] Configure local MySQL credentials
- [ ] Run `mvn clean package` successfully
- [ ] Test registration and login
- [ ] Submit a recipe and approve it as admin
- [ ] Test search, rating, and comments
- [ ] Capture real screenshots for the presentation
- [ ] Push code to a public GitHub repository without credentials or `target/`
- [ ] Explain the schema, JDBC DAO layer, and Servlet/JSP request flow

## Important
This starter has not been verified on your machine; test it before submission. Review marks cannot be guaranteed. Recipe images use optional external URLs; image upload storage is not included.
