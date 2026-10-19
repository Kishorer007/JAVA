
CREATE DATABASE Employee;
USE employee;


CREATE TABLE Employee (
    emp_id INT PRIMARY KEY,
    emp_name VARCHAR(50),
    department VARCHAR(30),
    salary DECIMAL(10,2),
    city VARCHAR(30),
    joining_date DATE
);

-- SAMPLE DATA

INSERT INTO Employee VALUES
(101,'John','IT',60000,'Chennai','2022-01-15'),
(102,'David','HR',45000,'Bangalore','2021-03-10'),
(103,'Smith','IT',70000,'Chennai','2020-07-12'),
(104,'Mary','Finance',55000,'Mumbai','2023-01-20'),
(105,'James','HR',48000,'Delhi','2022-05-05'),
(106,'Linda','Finance',65000,'Mumbai','2021-08-18');


-- 1. Total employees in each department
SELECT department, COUNT(*) AS total_employees
FROM Employee
GROUP BY department;

-- 2. Average salary in each department
SELECT department, AVG(salary) AS average_salary
FROM Employee
GROUP BY department;

-- 3. Departments having more than one employee
SELECT department, COUNT(*) AS total_employees
FROM Employee
GROUP BY department
HAVING COUNT(*) > 1;

-- 4. Highest salary in each department
SELECT department, MAX(salary) AS highest_salary
FROM Employee
GROUP BY department;

-- 5. Lowest salary in each department
SELECT department, MIN(salary) AS lowest_salary
FROM Employee
GROUP BY department;

-- 6. Departments with average salary greater than 50000
SELECT department, AVG(salary) AS average_salary
FROM Employee
GROUP BY department
HAVING AVG(salary) > 50000;

-- 7. Total salary expenditure for each department
SELECT department, SUM(salary) AS total_salary
FROM Employee
GROUP BY department;

-- 8. Employees sorted by salary descending
SELECT *
FROM Employee
ORDER BY salary DESC;

-- 9. Sort by department and salary descending
SELECT *
FROM Employee
ORDER BY department ASC, salary DESC;

-- 10. Cities having more than one employee
SELECT city, COUNT(*) AS total_employees
FROM Employee
GROUP BY city
HAVING COUNT(*) > 1;

-- 11. Total salary paid in each city
SELECT city, SUM(salary) AS total_salary
FROM Employee
GROUP BY city;

-- 12. Departments ordered by total salary descending
SELECT department, SUM(salary) AS total_salary
FROM Employee
GROUP BY department
ORDER BY total_salary DESC;

-- 13. Employees in each department earning above 50000
SELECT department, COUNT(*) AS employee_count
FROM Employee
WHERE salary > 50000
GROUP BY department;

-- 14. Difference between highest and lowest salary
SELECT department,
       MAX(salary) - MIN(salary) AS salary_difference
FROM Employee
GROUP BY department;

-- 15. Top 3 highest-paid employees
SELECT *
FROM Employee
ORDER BY salary DESC
LIMIT 3;
