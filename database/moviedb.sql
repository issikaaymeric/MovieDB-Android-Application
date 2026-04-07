CREATE DATABASE IF NOT EXISTS moviedb;

USE moviedb;

CREATE TABLE IF NOT EXISTS movies (
    movieID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    title VARCHAR(255) NOT NULL, 
    year INT NOT NULL,
    director VARCHAR(255) NOT NULL
);

INSERT INTO movies (movieID, title, year, director) VALUES
(109, 'Pousse-pousse', 1982, 'Kamwa');

SELECT * FROM movies LIMIT 1000;

