# Documentation for Book Lending Backend API

## Introduction

This document provides the developer documentation for the book lending backend API. The API is implemented using Java, Spring Boot, and Spring, and the data is stored in a MySQL database.

## Prerequisites

Before running the application, make sure you have Java Development Kit (JDK) (This app is developed with Java 8), MySQL and Maven installed in your system. If Maven is not installed, you can follow the instructions below to install it.

For MacOS, you can use Homebrew to install Maven:

```bash
brew install maven
```

For Windows, download the Maven binary zip file from the official website, extract it, and then add the `bin` directory of the extracted directory to your system PATH.

For Linux (Ubuntu), you can use the apt package manager to install Maven:

```bash
sudo apt update
sudo apt install maven
```

## Running the Application

To run the application, create a database instance with the name "lendingdb" first. One alternative is through MySQL CLI:

```bash
mysql
```

```sql
> CREATE DATABASE lendingdb;
```

then, navigate to the root directory of the project where the `pom.xml` file is located. Run the following command:

```bash
mvn spring-boot:run
```

This command compiles the application and starts the Spring Boot server. Once the server is running, you can access the application at `http://localhost:8080`.

## Structure

The application is structured into four directories:

- `model/`
- `repository/`
- `service/`
- `controller/`

Each directory contains implementations specific to four entities: User, Book, Card, and Payment.

## Models

There are four models:

1. **User**: Contains user details.
    - `mail`: Email of the user. (Primary Key)
    - `password`: Password for the user.
    - `activity`: Activity status of the user.

2. **Book**: Contains book details.
    - `isbn`: ISBN of the book. (Primary Key)
    - `author`: Author of the book.
    - `title`: Title of the book.
    - `dailyCost`: Daily cost for lending the book.

3. **Card**: Contains card details.
    - `cardNumber`: Card number. (Primary Key)
    - `balance`: Balance on the card.

4. **Payment**: Contains payment details.
    - `user_mail`: Email of the user.
    - `book_isbn`: ISBN of the book.
    - `card_cardNumber`: Card number.
    - `startDate`: Start date of the lending period.
    - `endDate`: End date of the lending period.
    - `cost`: Cost of the lending.

The Payment model has foreign key relationships to the User, Book, and Card models. Payment instances are identified by an auto-incrementing ID.

## API Routes and Services

Each model has its own route and a set of services for basic CRUD (Create, Read, Update, Delete) operations.

- **User Route**: `user/`
    - (POST) `add/`: Add a new user. Specify user details as Request Body.
    - (GET) `{mail}/`: Get existing user with given mail.
    - (UPDATE) `update/{mail}/`: Update an existing user. Specify new user details as Request Body.
    - (DELETE) `delete/{mail}/`: Delete existing user with given mail.

- **Book Route**: `book/`
    - (POST) `add/`: Add a new book.
    - (GET) `{isbn}/`: Get existing books.
    - (PUT) `update/{isbn}`: Update an existing book.
    - (DELETE) `delete/{isbn}`: Delete a book.

- **Card Route**: `card/`
    - (POST) `add/`: Add a new card.
    - (GET) `get/`: Get existing cards.
    - (PUT) `update/`: Update an existing card.
    - (DELETE) `{id}/`: Delete a card.

- **Payment Route**: `payment/`
    - (POST) `add/`: Add a new payment.
    - (GET) `get/`: Get existing payments.
    - (PUT) `update/`: Update an existing payment.
    - (DELETE) `{id}/`: Delete a payment.

## Business Logic

The application also implements some basic business rules:

- When adding a payment, the application checks if the card has sufficient balance and if the book is available for the given dates.
- After a successful payment, the application updates the balance on the card.
- When updating a payment, the application does not allow changing the user, book, or card. Only the dates can be modified.
- When updating a payment, the balance of the used card is adjusted accordingly. If the new number of days are fewer, the corresponding balance is returned to the card and vice versa.

## Postman Collection

The Postman collection for testing functionalities of the app and constraints mentioned above can be found in the root directory as a JSON file which can be imported into Postman, `BookLendingAPI.postman_collection.json`. 
<img width="1306" alt="Screenshot 2023-07-17 at 09 17 01" src="https://github.com/rburaksaritas/allianz/assets/86561984/5f48a52b-5c9f-433f-b162-b6e5d3599534">




## Data Persistence

The application uses the Spring Data JPA library for data persistence. This library automates the creation of tables in the MySQL database based on the defined models. The Payment table has foreign key relationships with the User, Book, and Card tables.

## Database Configuration

The database configuration details are specified in the `application.properties` file located in the `src/main/resources/` directory. Modify username and password according to your MySQL credentials.

```properties
spring.jpa.hibernate.ddl-auto=update

spring.datasource.url=jdbc:mysql://localhost:3306/lendingdb
spring.datasource.username=aztr
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

## Conclusion

This document provides a brief overview of the structure and implementation of the book lending backend API. The API implements basic CRUD operations for four entities: User, Book, Card, and Payment. It also enforces some business rules to ensure data integrity and correctness.

---

<p align="center">
Prepared by:<br>
<b>Ramazan Burak Sarıtaş</b><br>
Intern @ Allianz Türkiye<br>
07/2023<br>
</p>
