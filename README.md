# CanAir Visual-Based Programming Course Project

This project is developed to create a **ticket management system** for a fictional airline company named **CanAir**. The application allows users to register, authenticate, search for flights, and manage their seat assignments seamlessly through a user-friendly graphical interface.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Database Setup](#database-setup)
- [Application Structure](#application-structure)
- [Usage](#usage)
- [Screenshots](#screenshots)
- [Contributing](#contributing)

## Features

- **User Registration and Authentication:** Secure user sign-up and login functionalities.
- **Flight Management:** Search for existing flights or create new ones based on departure and arrival cities along with the flight date.
- **Seat Assignment:** Automatically assigns the first available seat to the user and allows seat selection and updates.
- **User-Friendly Interface:** Intuitive GUI built with Java Swing for easy navigation and interaction.
- **Database Integration:** Robust backend using MariaDB to handle data storage and retrieval efficiently.

## Technologies Used

- **Programming Language:** Java
- **GUI Framework:** Java Swing
- **Database:** MariaDB
- **Libraries:**
  - `com.toedter.calendar.JDateChooser` for date selection
- **Version Control:** GitHub

## Database Setup

To set up the MariaDB database for the CanAir project, execute the following SQL commands:

### Create Tables

```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE passwords (
    user_id INT,
    password VARCHAR(255),
    PRIMARY KEY (user_id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE flights (
    id INT AUTO_INCREMENT PRIMARY KEY,
    departure_city VARCHAR(50),
    arrival_city VARCHAR(50),
    flight_date DATE
);

CREATE TABLE user_flights (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    flight_id INT,
    seat_choice VARCHAR(10),
    travel_insurance BOOLEAN,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (flight_id) REFERENCES flights(id)
);

CREATE TABLE seats (
    id INT AUTO_INCREMENT PRIMARY KEY,
    flight_id INT,
    seat_number VARCHAR(10),
    available BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE CASCADE
);
```

### Create Trigger for Seat Allocation

```sql
DELIMITER //
CREATE TRIGGER add_seats_after_flight_creation
AFTER INSERT ON flights
FOR EACH ROW
BEGIN
    DECLARE seat_index INT DEFAULT 1;
    WHILE seat_index <= 20 DO
        INSERT INTO seats (flight_id, seat_number, available) VALUES (NEW.id, CONCAT(seat_index), TRUE);
        SET seat_index = seat_index + 1;
    END WHILE;
END;
//
DELIMITER ;
```

### Querying the Database

```sql
SELECT * FROM users;
SELECT * FROM passwords;
SELECT * FROM flights;
SELECT * FROM user_flights;
SELECT * FROM seats;
```

### Cleaning Up the Database

```sql
DELETE FROM user_flights;
DELETE FROM seats;
DELETE FROM flights;
DELETE FROM passwords;
DELETE FROM users;

ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE flights AUTO_INCREMENT = 1;
ALTER TABLE user_flights AUTO_INCREMENT = 1;
ALTER TABLE seats AUTO_INCREMENT = 1;
ALTER TABLE passwords AUTO_INCREMENT = 1;
```

## Application Structure

The project consists of several Java classes that handle different aspects of the application:

- **Database.java:** Manages all database operations, including user registration, authentication, flight management, and seat assignments.
- **Login.java:** Provides the user interface for user login.
- **Register.java:** Provides the user interface for new user registration.
- **Main.java:** The entry point of the application, handling the main window and flight search functionality.
- **Second.java:** Manages the flight details window, including seat selection and assignment.

## Usage

1. **Setup the Database:**
   - Install MariaDB and execute the provided SQL commands to set up the necessary tables and triggers.

2. **Configure Database Connection:**
   - Update the `Database.java` file with your MariaDB connection details if they differ from the defaults.

3. **Run the Application:**
   - Compile and run the `Main.java` class to start the CanAir application.

4. **Register and Login:**
   - Use the "Üye Ol" (Sign Up) button to create a new account.
   - Use the "Giriş Yap" (Login) button to authenticate with your credentials.

5. **Search for Flights:**
   - Select departure and arrival cities along with the flight date to search for available flights.
   - If a flight doesn't exist, it will be created automatically.

6. **Manage Seats:**
   - After selecting a flight, view flight details and choose an available seat.
   - You can update your seat assignment as needed.

## Screenshots

<img src="https://github.com/can61cebi/CanAir/blob/main/Development_Stage_01.png" width="400">

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request for any enhancements or bug fixes.
