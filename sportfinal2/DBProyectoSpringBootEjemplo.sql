DROP DATABASE IF EXISTS DBProyectoSpringBootEjemplo;
CREATE DATABASE DBProyectoSpringBootEjemplo;
USE DBProyectoSpringBootEjemplo;

CREATE TABLE users (
    id INT not null AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100) unique,
    primary key pk_id(id)
);
select * from users;