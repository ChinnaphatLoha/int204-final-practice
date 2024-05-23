# CRUD REST API Project with MySQL and Spring Boot

This project is a CRUD REST API built using Spring Boot and MySQL. It allows managing a database of classic films, their genres, and directors.

## Table of Contents

- [CRUD REST API Project with MySQL and Spring Boot](#crud-rest-api-project-with-mysql-and-spring-boot)
  - [Table of Contents](#table-of-contents)
  - [Requirements](#requirements)
  - [Setup](#setup)
    - [Database Setup](#database-setup)
    - [Project Setup](#project-setup)
    - [Configuration](#configuration)
  - [Running the Application](#running-the-application)
  - [API Documentation](#api-documentation)
  - [Testing](#testing)
  - [Troubleshooting](#troubleshooting)

## Requirements

- Java 17
- Maven 3.8+
- MySQL 8.0+
- Postman (for testing API endpoints)

## Setup

### Database Setup

1. **Create the database and tables:**

   Execute the following SQL script in your MySQL database:

   ```sql
   CREATE DATABASE `classic-film`;

   USE `classic-film`;

   CREATE TABLE genres (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(50) NOT NULL UNIQUE
   );

   CREATE TABLE directors (
       id INT AUTO_INCREMENT PRIMARY KEY,
       first_name VARCHAR(50) NOT NULL,
       last_name VARCHAR(50) NOT NULL,
       date_of_birth DATE NOT NULL,
       gender ENUM('Male', 'Female', 'Other') NOT NULL
   );

   CREATE TABLE movies (
       id INT AUTO_INCREMENT PRIMARY KEY,
       title VARCHAR(100) NOT NULL,
       release_year SMALLINT NOT NULL,
       genre_id INT,
       director_id INT,
       FOREIGN KEY (genre_id) REFERENCES genres(id),
       FOREIGN KEY (director_id) REFERENCES directors(id)
   );

   INSERT INTO genres (name) VALUES 
   ('Action'),
   ('Comedy'),
   ('Drama'),
   ('Fantasy'),
   ('Horror'),
   ('Romance'),
   ('Sci-Fi'),
   ('Thriller');

   INSERT INTO directors (first_name, last_name, date_of_birth, gender) VALUES
   ('Steven', 'Spielberg', '1946-12-18', 'Male'),
   ('Christopher', 'Nolan', '1970-07-30', 'Male'),
   ('James', 'Cameron', '1954-08-16', 'Male'),
   ('Patty', 'Jenkins', '1971-07-24', 'Female');

   INSERT INTO movies (title, release_year, genre_id, director_id) VALUES
   ('Iron Man', 2008, 1, 1),
   ('The Avengers', 2012, 1, 1),
   ('Inception', 2010, 7, 2),
   ('Titanic', 1997, 6, 3),
   ('Wonder Woman', 2017, 1, 4);

   COMMIT;
   ```

### Project Setup

1. **Clone the repository:**

   ```sh
   git clone <repository-url>
   cd <repository-directory>
   ```

2. **Build the project with Maven:**

   ```sh
   mvn clean install
   ```

### Configuration

Configure the MySQL connection in `src/main/resources/application.properties`:

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=${MYSQL_USERNAME:root}
spring.datasource.password=${MYSQL_PASSWORD:password}
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3306}/${MYSQL_DATABASE_NAME:classic-film}
spring.jpa.hibernate.ddl-auto=none
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
```

Adjust the `${MYSQL_USERNAME}`, `${MYSQL_PASSWORD}`, `${MYSQL_HOST}`, `${MYSQL_PORT}`, and `${MYSQL_DATABASE_NAME}` as needed.

## Running the Application

Run the Spring Boot application:

```sh
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

## API Documentation

The API provides endpoints to perform CRUD operations on movies, directors, and genres.

- **Movies:**
    - GET `/movies` - Retrieve all movies
    - GET `/movies/{id}` - Retrieve a specific movie by ID
    - POST `/movies` - Create a new movie
    - PUT `/movies/{id}` - Update an existing movie
    - DELETE `/movies/{id}` - Delete a movie

- **Directors:**
    - GET `/directors` - Retrieve all directors
    - GET `/directors/{id}` - Retrieve a specific director by ID
    - POST `/directors` - Create a new director
    - PUT `/directors/{id}` - Update an existing director
    - DELETE `/directors/{id}` - Delete a director

- **Genres:**
    - GET `/genres` - Retrieve all genres
    - GET `/genres/{id}` - Retrieve a specific genre by ID
    - POST `/genres` - Create a new genre
    - PUT `/genres/{id}` - Update an existing genre
    - DELETE `/genres/{id}` - Delete a genre

## Testing

For testing the API endpoints, import the provided `Final_Exam_INT204.postman_collection.json` into Postman.

## Troubleshooting

- Ensure your MySQL server is running and accessible.
- Verify the database credentials in the `application.properties` file.
- Check the console output for any error messages and stack traces.