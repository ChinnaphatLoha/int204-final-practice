CREATE DATABASE `classic-film`;

USE `classic-film`;

CREATE TABLE genres
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE directors
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    first_name    VARCHAR(50)                      NOT NULL,
    last_name     VARCHAR(50)                      NOT NULL,
    date_of_birth DATE                             NOT NULL,
    gender        ENUM ('Male', 'Female', 'Other') NOT NULL
);

CREATE TABLE movies
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(100) NOT NULL,
    release_year SMALLINT     NOT NULL,
    genre_id     INT,
    director_id  INT,
    FOREIGN KEY (genre_id) REFERENCES genres (id),
    FOREIGN KEY (director_id) REFERENCES directors (id)
);

INSERT INTO genres (name)
VALUES ('Action'),
       ('Comedy'),
       ('Drama'),
       ('Fantasy'),
       ('Horror'),
       ('Romance'),
       ('Sci-Fi'),
       ('Thriller');

INSERT INTO directors (first_name, last_name, date_of_birth, gender)
VALUES ('Steven', 'Spielberg', '1946-12-18', 'Male'),
       ('Christopher', 'Nolan', '1970-07-30', 'Male'),
       ('James', 'Cameron', '1954-08-16', 'Male'),
       ('Patty', 'Jenkins', '1971-07-24', 'Female');

INSERT INTO movies (title, release_year, genre_id, director_id)
VALUES ('Iron Man', 2008, 1, 1),
       ('The Avengers', 2012, 1, 1),
       ('Inception', 2010, 7, 2),
       ('Titanic', 1997, 6, 3),
       ('Wonder Woman', 2017, 1, 4);

COMMIT;
