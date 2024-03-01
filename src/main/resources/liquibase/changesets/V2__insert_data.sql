INSERT INTO users (username, full_name, birth_date, password)
VALUES ('john_doe', 'John Doe', '1999-01-29', '$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W'),
       ('jane_smith', 'Jane Smith', '1998-01-29', '$2a$10$fFLij9aYgaNCFPTL9WcA/uoCRukxnwf.vOQ8nrEEOskrCNmGsxY7m');

INSERT INTO accounts (user_id, initial_deposit, balance)
VALUES (1, 1000.00, 1000.0),
       (2, 500.00, 500.00);

INSERT INTO users_phone_numbers (user_id, phone_number)
VALUES (1, '71234567890'),
       (2, '71234567891');

INSERT INTO users_emails (user_id, email)
VALUES (1, 'qefkako@ad'),
       (2, 'sfewee@sa');
