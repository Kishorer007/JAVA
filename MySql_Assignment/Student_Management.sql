CREATE DATABASE Students;
USE Students;

CREATE TABLE Students (
    student_id INT PRIMARY KEY,
    student_name VARCHAR(50),
    department VARCHAR(30),
    marks INT
);

-- SAMPLE DATA

INSERT INTO Students VALUES
(1,'Arun','IT',85),
(2,'Priya','IT',90),
(3,'Kumar','IT',78),
(4,'Divya','IT',88),
(5,'Ravi','IT',72),
(6,'Meena','IT',95),
(7,'John','HR',70),
(8,'Mary','HR',80),
(9,'David','HR',75),
(10,'James','HR',65),
(11,'Linda','Finance',92),
(12,'Smith','Finance',86),
(13,'John K','Finance',78),
(14,'Rani','ECE',68),
(15,'Vijay','ECE',82);


-- 31. Average marks in each department
SELECT department, AVG(marks) AS average_marks
FROM Students
GROUP BY department;

-- 32. Departments with average marks above 75
SELECT department, AVG(marks) AS average_marks
FROM Students
GROUP BY department
HAVING AVG(marks) > 75;

-- 33. Highest mark in each department
SELECT department, MAX(marks) AS highest_marks
FROM Students
GROUP BY department;

-- 34. Total students in each department
SELECT department, COUNT(*) AS total_students
FROM Students
GROUP BY department;

-- 35. Departments having more than 5 students
SELECT department, COUNT(*) AS total_students
FROM Students
GROUP BY department
HAVING COUNT(*) > 5;

-- 36. Departments sorted by average marks descending
SELECT department, AVG(marks) AS average_marks
FROM Students
GROUP BY department
ORDER BY average_marks DESC;

-- 37. Top 3 departments based on average marks
SELECT department, AVG(marks) AS average_marks
FROM Students
GROUP BY department
ORDER BY average_marks DESC
LIMIT 3;

-- 38. Departments with average marks between 70 and 90
SELECT department, AVG(marks) AS average_marks
FROM Students
GROUP BY department
HAVING AVG(marks) BETWEEN 70 AND 90;

-- 39. Total marks in each department
SELECT department, SUM(marks) AS total_marks
FROM Students
GROUP BY department;

-- 40. Departments sorted by number of students
SELECT department, COUNT(*) AS total_students
FROM Students
GROUP BY department
ORDER BY total_students DESC;

-- 41. Lowest mark in each department
SELECT department, MIN(marks) AS lowest_marks
FROM Students
GROUP BY department;

-- 42. Departments where highest mark is greater than 90
SELECT department, MAX(marks) AS highest_marks
FROM Students
GROUP BY department
HAVING MAX(marks) > 90;

-- 43. Number of students scoring above 80 in each department
SELECT department, COUNT(*) AS students_above_80
FROM Students
WHERE marks > 80
GROUP BY department;

-- 44. Departments where more than 3 students scored above 75
SELECT department, COUNT(*) AS students_above_75
FROM Students
WHERE marks > 75
GROUP BY department
HAVING COUNT(*) > 3;

-- 45. Departments ordered by highest mark descending
SELECT department, MAX(marks) AS highest_marks
FROM Students
GROUP BY department
ORDER BY highest_marks DESC;
