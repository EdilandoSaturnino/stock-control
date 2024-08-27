CREATE DATABASE IF NOT EXISTS stock_control;

USE stock_control;

CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    role ENUM('ADMIN', 'EMPLOYEE') NOT NULL
);

INSERT INTO users (username, password, role) VALUES
('admin', 'admin123', 'ADMIN'),
('employee', 'emp123', 'EMPLOYEE');