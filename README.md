# Product Management API - Assessment Submission - Jarvis

Hi! Thanks for the opportunity to work on this assessment. This repository contains my implementation of the Product Management REST API. 

I've set up the project using the standard Controller -> Service -> Repository architecture and included Swagger for easy API testing.

# Environment Setup
Java Version: 21
Spring Boot Version: 3.3.4
Database: MySQL

# How to Run the Application
1. Open MySQL Command Line Client and create the database by running: 
   `CREATE DATABASE product_db;`
2. Update the `src/main/resources/application.properties` file with your specific MySQL root password.
3. Open a terminal in the root directory of the project.
4. Run the application using the Maven wrapper
5. Once the server starts, access the Swagger API documentation at: 
   `http://localhost:8080/swagger-ui/index.html`