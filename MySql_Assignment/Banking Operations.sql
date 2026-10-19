

CREATE DATABASE Banking;

Use Banking;

CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    city VARCHAR(50)
);

CREATE TABLE accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    account_type VARCHAR(20),
    balance DECIMAL(10,2),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- INSERT SAMPLE DATA

INSERT INTO customers (name, city)
VALUES
('Janani', 'Chennai'),
('Arun', 'Coimbatore'),
('Priya', 'Madurai'),
('Karthik', 'Salem');

INSERT INTO accounts (customer_id, account_type, balance)
VALUES
(1, 'Savings', 50000),
(1, 'Current', 20000),
(2, 'Savings', 30000),
(3, 'Savings', 15000),
(4, 'Current', 40000);


-- 1. Accounts with balance greater than 20000
SELECT * FROM accounts
WHERE balance > 20000;

-- 2. Customers living in Chennai
SELECT * FROM customers
WHERE city = 'Chennai';

-- 3. Accounts with balance between 20000 and 50000
SELECT * FROM accounts
WHERE balance BETWEEN 20000 AND 50000;

-- 4. Customers whose names start with J
SELECT * FROM customers
WHERE name LIKE 'J%';

-- 5. Accounts of type Savings or Current
SELECT * FROM accounts
WHERE account_type IN ('Savings', 'Current');

-- 6. Accounts that are not Savings
SELECT * FROM accounts
WHERE account_type <> 'Savings';

-- 7. Customers whose names contain the letter a
SELECT * FROM customers
WHERE name LIKE '%a%';

-- 8. Accounts with balance less than or equal to 30000
SELECT * FROM accounts
WHERE balance <= 30000;

-- 9. Customers who are not from Madurai
SELECT * FROM customers
WHERE city <> 'Madurai';

-- 10. Accounts with balance not between 10000 and 40000
SELECT * FROM accounts
WHERE balance NOT BETWEEN 10000 AND 40000;

-- 11. Customers whose names end with i
SELECT * FROM customers
WHERE name LIKE '%i';

-- 12. Accounts with balance equal to 50000
SELECT * FROM accounts
WHERE balance = 50000;

-- 13. Customers from Chennai or Salem
SELECT * FROM customers
WHERE city IN ('Chennai', 'Salem');

-- 14. Accounts with balance greater than 10000 and less than 40000
SELECT * FROM accounts
WHERE balance > 10000 AND balance < 40000;

-- 15. Accounts whose type is not Current
SELECT * FROM accounts
WHERE account_type NOT IN ('Current');


-- 16. Accounts sorted by balance descending
SELECT * FROM accounts
ORDER BY balance DESC;

-- 17. Customers sorted alphabetically
SELECT * FROM customers
ORDER BY name ASC;

-- 18. Sort by account type and balance descending
SELECT * FROM accounts
ORDER BY account_type ASC, balance DESC;



-- 19. Total balance of all accounts
SELECT SUM(balance) AS total_balance
FROM accounts;

-- 20. Average account balance
SELECT AVG(balance) AS average_balance
FROM accounts;

-- 21. Maximum account balance
SELECT MAX(balance) AS maximum_balance
FROM accounts;

-- 22. Minimum account balance
SELECT MIN(balance) AS minimum_balance
FROM accounts;

-- 23. Total number of customers
SELECT COUNT(*) AS total_customers
FROM customers;


-- 24. Total balance grouped by account type
SELECT account_type, SUM(balance) AS total_balance
FROM accounts
GROUP BY account_type;

-- 25. Average balance for each account type
SELECT account_type, AVG(balance) AS average_balance
FROM accounts
GROUP BY account_type;

-- 26. Account types with average balance greater than 20000
SELECT account_type, AVG(balance) AS average_balance
FROM accounts
GROUP BY account_type
HAVING AVG(balance) > 20000;

-- 27. Number of accounts for each customer
SELECT customer_id, COUNT(*) AS account_count
FROM accounts
GROUP BY customer_id;

-- 28. Customers having more than one account
SELECT c.customer_id, c.name,
       COUNT(a.account_id) AS account_count
FROM customers c
JOIN accounts a
ON c.customer_id = a.customer_id
GROUP BY c.customer_id, c.name
HAVING COUNT(a.account_id) > 1;



-- 29. Customer names with account balances
SELECT c.name, a.balance
FROM customers c
JOIN accounts a
ON c.customer_id = a.customer_id;

-- 30. All customers and their accounts
SELECT c.name, a.account_id, a.account_type, a.balance
FROM customers c
LEFT JOIN accounts a
ON c.customer_id = a.customer_id;

-- 31. All accounts with corresponding customer details
SELECT a.*, c.name, c.city
FROM accounts a
JOIN customers c
ON a.customer_id = c.customer_id;

-- 32. Customer names and account types where balance > 20000
SELECT c.name, a.account_type, a.balance
FROM customers c
JOIN accounts a
ON c.customer_id = a.customer_id
WHERE a.balance > 20000;

-- 33. Customers with their total balance using JOIN
SELECT c.customer_id, c.name,
       COALESCE(SUM(a.balance), 0) AS total_balance
FROM customers c
LEFT JOIN accounts a
ON c.customer_id = a.customer_id
GROUP BY c.customer_id, c.name;

-- 34. Customer names and balances sorted by balance
SELECT c.name, a.balance
FROM customers c
JOIN accounts a
ON c.customer_id = a.customer_id
ORDER BY a.balance DESC;

-- 35. Number of accounts for each city
SELECT c.city, COUNT(a.account_id) AS account_count
FROM customers c
LEFT JOIN accounts a
ON c.customer_id = a.customer_id
GROUP BY c.city;


-- 36. Accounts with balance greater than average balance
SELECT *
FROM accounts
WHERE balance > (
    SELECT AVG(balance)
    FROM accounts
);

-- 37. Customers who have accounts
SELECT *
FROM customers
WHERE customer_id IN (
    SELECT customer_id
    FROM accounts
);

-- 38. Customers who do not have any accounts
SELECT *
FROM customers c
WHERE NOT EXISTS (
    SELECT 1
    FROM accounts a
    WHERE a.customer_id = c.customer_id
);

-- 39. Account(s) with maximum balance
SELECT *
FROM accounts
WHERE balance = (
    SELECT MAX(balance)
    FROM accounts
);

-- 40. Customers whose total balance is greater than 40000
SELECT c.customer_id, c.name,
       SUM(a.balance) AS total_balance
FROM customers c
JOIN accounts a
ON c.customer_id = a.customer_id
GROUP BY c.customer_id, c.name
HAVING SUM(a.balance) > 40000;
