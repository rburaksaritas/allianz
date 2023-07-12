CREATE DATABASE IF NOT EXISTS lendingdb;
USE lendingdb;

CREATE TABLE Book (
      isbn VARCHAR(255) PRIMARY KEY,
      author VARCHAR(255),
      title VARCHAR(255),
      dailyCost DOUBLE
);

CREATE TABLE User (
      mail VARCHAR(255) PRIMARY KEY,
      password VARCHAR(255),
      active BOOLEAN
);

CREATE TABLE Payment (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     mail VARCHAR(255),
     isbn VARCHAR(255),
     creditCardDetails VARCHAR(255),
     startDate VARCHAR(255),
     endDate VARCHAR(255),
     FOREIGN KEY (mail) REFERENCES User (mail),
     FOREIGN KEY (isbn) REFERENCES Book (isbn)
);

