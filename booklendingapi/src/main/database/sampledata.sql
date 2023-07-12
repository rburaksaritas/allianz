USE lendingdb;

-- Insert sample books
INSERT INTO Book (isbn, author, title, dailyCost)
VALUES
    ('9780345339706', 'J.R.R. Tolkien', 'The Lord of the Rings', 1.99),
    ('9780061124952', 'Harper Lee', 'To Kill a Mockingbird', 2.50),
    ('9780140186425', 'Jane Austen', 'Pride and Prejudice', 1.75),
    ('9780553573404', 'George R.R. Martin', 'A Game of Thrones', 2.25);

-- Insert sample users
INSERT INTO User (mail, password, active)
VALUES
    ('user1@example.com', 'password1', true),
    ('user2@example.com', 'password2', true),
    ('user3@example.com', 'password3', false);

-- Insert sample payments
INSERT INTO Payment (mail, isbn, creditCardDetails, startDate, endDate)
VALUES
    ('user1@example.com', '9780345339706', '**** **** **** 1234', '2023-06-01', '2023-06-07'),
    ('user2@example.com', '9780061124952', '**** **** **** 5678', '2023-06-15', '2023-06-22'),
    ('user3@example.com', '9780140186425', '**** **** **** 9012', '2023-07-01', '2023-07-08'),
    ('user1@example.com', '9780553573404', '**** **** **** 3456', '2023-07-10', '2023-07-17');
