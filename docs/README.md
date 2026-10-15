# Recipe Haven — Project Documentation

## 1. Project Overview
Recipe Haven is an Online Recipe Sharing Platform designed to help users discover, share, and manage recipes through a web-based application.

## 2. Main Features
- User registration and login
- Browse and discover approved recipes
- View recipe details and instructions
- Share and manage personal recipes
- Rate recipes and post comments
- Admin dashboard for recipe moderation

## 3. Technology Stack
- **Backend:** Java, Java Servlets
- **Frontend:** JSP, HTML, CSS, JavaScript
- **Database:** MySQL
- **Database connectivity:** JDBC
- **Build tool:** Maven
- **Web server:** Apache Tomcat 10.1

## 4. Database Design
The database includes tables for users, recipes, ratings, comments, and activity logs. Primary keys identify records, while foreign keys maintain relationships between users, recipes, ratings, and comments.

## 5. Application Architecture
The application follows a layered structure:
- **Presentation layer:** JSP, HTML, CSS, and JavaScript
- **Controller layer:** Java Servlets
- **Data access layer:** DAO classes using JDBC
- **Database layer:** MySQL

## 6. Testing Status
The Maven build completed successfully. Full application testing, including registration, login, recipe operations, database connectivity, and admin moderation, should be verified before claiming these features are fully functional.

## 7. Repository
GitHub: https://github.com/dhruvdayal25/recipe-haven
