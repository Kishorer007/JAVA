
-- CREATE TABLES

CREATE DATABASE Railway;
USE railway;


CREATE TABLE trains (
    train_id INT PRIMARY KEY AUTO_INCREMENT,
    train_name VARCHAR(50),
    source VARCHAR(50),
    destination VARCHAR(50)
);

CREATE TABLE bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    train_id INT,
    passenger_name VARCHAR(50),
    fare DECIMAL(10,2),
    status VARCHAR(20),
    FOREIGN KEY (train_id) REFERENCES trains(train_id)
);

-- INSERT SAMPLE DATA

INSERT INTO trains (train_name, source, destination)
VALUES
('Express1', 'Chennai', 'Madurai'),
('Express2', 'Coimbatore', 'Salem'),
('Express3', 'Madurai', 'Chennai');

INSERT INTO bookings (train_id, passenger_name, fare, status)
VALUES
(1, 'Janani', 500, 'Confirmed'),
(1, 'Arun', 500, 'Waiting'),
(2, 'Priya', 300, 'Confirmed'),
(3, 'Karthik', 450, 'Cancelled'),
(2, 'Meena', 300, 'Confirmed');


-- 41. Retrieve bookings with fare greater than 400
SELECT *
FROM bookings
WHERE fare > 400;

-- 42. Find bookings where status is not Confirmed
SELECT *
FROM bookings
WHERE status <> 'Confirmed';

-- 43. Display trains starting from Chennai
SELECT *
FROM trains
WHERE source = 'Chennai';

-- 44. Retrieve bookings with fare between 300 and 500
SELECT *
FROM bookings
WHERE fare BETWEEN 300 AND 500;

-- 45. Find passengers whose names start with A
SELECT *
FROM bookings
WHERE passenger_name LIKE 'A%';


-- 46. Retrieve train names along with passenger names
SELECT t.train_name, b.passenger_name
FROM trains t
JOIN bookings b
ON t.train_id = b.train_id;

-- 47. Count number of bookings for each train
SELECT t.train_name,
       COUNT(b.booking_id) AS booking_count
FROM trains t
LEFT JOIN bookings b
ON t.train_id = b.train_id
GROUP BY t.train_id, t.train_name;

-- 48. Display train names and total fare collected
-- Only Confirmed bookings are counted as collected fare
SELECT t.train_name,
       COALESCE(
           SUM(
               CASE
                   WHEN b.status = 'Confirmed' THEN b.fare
                   ELSE 0
               END
           ), 0
       ) AS total_fare_collected
FROM trains t
LEFT JOIN bookings b
ON t.train_id = b.train_id
GROUP BY t.train_id, t.train_name
ORDER BY total_fare_collected DESC;


-- 49. Find bookings with fare equal to the highest fare
SELECT *
FROM bookings
WHERE fare = (
    SELECT MAX(fare)
    FROM bookings
);

-- 50. Retrieve trains that have more than one booking
SELECT *
FROM trains
WHERE train_id IN (
    SELECT train_id
    FROM bookings
    GROUP BY train_id
    HAVING COUNT(*) > 1
);
